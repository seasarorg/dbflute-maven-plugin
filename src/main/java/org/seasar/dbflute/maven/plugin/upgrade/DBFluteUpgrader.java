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
package org.seasar.dbflute.maven.plugin.upgrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.dbflute.maven.plugin.util.ResourceFileUtil;

/**
 * DBFluteUpgrader downloads dbflute-*.zip, extracts it and replaces _project.*.
 * 
 * @author shinsuke
 *
 */
public class DBFluteUpgrader {
    protected DBFluteContext context;

    public DBFluteUpgrader(DBFluteContext context) {
        this.context = context;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

        File dbfluteDir = context.getDbfluteDir();
        File dbfluteClientDir = context.getDbfluteClientDir();
        if (!dbfluteClientDir.isDirectory()) {
            throw new MojoFailureException(dbfluteClientDir.getAbsolutePath()
                    + " does not exist.");
        }

        // Check schemaName
        if (StringUtils.isBlank(context.getSchemaName())) {
            throw new MojoFailureException("Missing schemaName.");
        }

        if (!dbfluteDir.exists()) {
            LogUtil.getLog().info("Creating " + dbfluteDir.getAbsolutePath());
            ResourceFileUtil
                    .unzip(context.getDownloadInputStream(), dbfluteDir);
        }

        LogUtil.getLog().info("Creating " + dbfluteClientDir.getAbsolutePath());
        File clientZipFile = new File(dbfluteDir,
                "etc/client-template/dbflute_dfclient.zip");
        if (!clientZipFile.exists()) {
            throw new MojoFailureException(clientZipFile.getAbsolutePath()
                    + " does not exist.");
        }

        // create temp dir
        File tempDir = ResourceFileUtil.createTempDir("dbflute-client", "");

        try {
            ResourceFileUtil.unzip(new FileInputStream(clientZipFile), tempDir);
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException(clientZipFile.getAbsolutePath()
                    + " is not found.", e);
        }

        // copy _project.*
        File srcFile;
        File destFile;
        try {
            srcFile = new File(tempDir, "dbflute_dfclient" + File.separator
                    + "_project.sh");
            destFile = new File(dbfluteClientDir, "_project.sh");
            LogUtil.getLog().info("Replacing " + destFile.getAbsolutePath());
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new MojoExecutionException("Could not replace _project.sh.",
                    e);
        }
        try {
            srcFile = new File(tempDir, "dbflute_dfclient" + File.separator
                    + "_project.bat");
            destFile = new File(dbfluteClientDir, "_project.bat");
            LogUtil.getLog().info("Replacing " + destFile.getAbsolutePath());
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new MojoExecutionException("Could not replace _project.bat.",
                    e);
        }

        // _project.sh
        Map<String, String> params = new HashMap<String, String>();
        putParam(params, "export MY_PROJECT_NAME=[^\r\n]+",
                "export MY_PROJECT_NAME=", context.getSchemaName());
        putParam(params, "export DBFLUTE_HOME=../mydbflute/[^\r\n]+",
                "export DBFLUTE_HOME=../mydbflute/", context.getDbfluteName());
        ResourceFileUtil.replaceContent(new File(context.getDbfluteClientDir(),
                "_project.sh"), params);

        // _project.bat
        params.clear();
        putParam(params, "set MY_PROJECT_NAME=[^\r\n]+",
                "set MY_PROJECT_NAME=", context.getSchemaName());
        putParam(params, "set DBFLUTE_HOME=..\\\\mydbflute\\\\[^\r\n]+",
                "set DBFLUTE_HOME=..\\\\mydbflute\\\\", context
                        .getDbfluteName());
        ResourceFileUtil.replaceContent(new File(context.getDbfluteClientDir(),
                "_project.bat"), params);
    }

    protected void putParam(Map<String, String> params, String key,
            String prefix, String value) {
        if (value != null) {
            params.put(key, prefix + value);
        }
    }
}
