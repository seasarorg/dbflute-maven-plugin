/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dbflute.maven.plugin.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;

/**
 * CommandExecutor executes dbflute's command.
 * 
 * @author shinsuke
 *
 */
public class CommandExecutor {
    protected DBFluteContext context;

    public CommandExecutor(DBFluteContext context) {
        this.context = context;
    }

    public void execute(String cmd) throws MojoExecutionException,
            MojoFailureException {
        File dbfluteClientDir = context.getDbfluteClientDir();
        if (!dbfluteClientDir.isDirectory()) {
            LogUtil.getLog().info(
                    "Create dbflute client directory. "
                            + "Try to run \'mvn dbflute:create-client\'.");
            return;
        }

        List<String> cmds = new ArrayList<String>();
        if (isWindows()) {
            cmds.add(cmd + ".bat");
        } else {
            cmds.add("sh");
            cmds.add(cmd + ".sh");
        }
        // TODO Mac?

        LogUtil.getLog().info(
                "Running " + StringUtils.join(cmds.toArray(), " "));
        ProcessBuilder builder = new ProcessBuilder(cmds);
        Process process;
        try {
            process = builder.directory(dbfluteClientDir).redirectErrorStream(
                    true).start();
        } catch (IOException e) {
            throw new MojoExecutionException("Could not run the command.", e);
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(process
                    .getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                LogUtil.getLog().info(line);
            }
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Could not print a command result.", e);
        } finally {
            IOUtils.closeQuietly(br);
        }

        try {
            int exitValue = process.waitFor();
            if (exitValue != 0) {
                throw new MojoFailureException(
                        "Build Failed. The exit value is " + exitValue + ".");
            }
        } catch (InterruptedException e) {
            throw new MojoExecutionException("Could not wait a process.", e);
        }

    }

    private boolean isWindows() {
        String name = System.getProperty("os.name");
        if (name != null && name.toLowerCase().indexOf("windows") >= 0) {
            return true;
        }
        return false;
    }
}
