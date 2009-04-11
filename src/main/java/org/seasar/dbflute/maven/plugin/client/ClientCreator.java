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
package org.seasar.dbflute.maven.plugin.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.dbflute.maven.plugin.util.ResourceFileUtil;
import org.seasar.framework.util.FileUtil;

/**
 * ClientCreator create dbflute client directory.
 * 
 * @author shinsuke
 *
 */
public class ClientCreator {
    protected DBFluteContext context;

    public ClientCreator(DBFluteContext context) {
        this.context = context;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        File dbfluteDir = context.getDbfluteDir();
        File dbfluteClientDir = context.getDbfluteClientDir();
        if (dbfluteClientDir.isDirectory()) {
            LogUtil.getLog().info(
                    dbfluteClientDir.getAbsolutePath() + " already exists.");
            return;
        }

        LogUtil.getLog().info("Creating " + dbfluteClientDir.getAbsolutePath());
        File clientZipFile = new File(dbfluteDir,
                "etc/client-template/dbflute_dfclient.zip");
        if (!clientZipFile.exists()) {
            throw new MojoFailureException(clientZipFile.getAbsolutePath()
                    + " does not exist.");
        }

        // create temp dir
        File tempDir = null;
        try {
            tempDir = File.createTempFile("dbflute-client", "");
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Could not create a temp directory. ", e);
        }
        if (!tempDir.delete()) {
            throw new MojoFailureException(
                    "Could not create a temp directory: "
                            + tempDir.getAbsolutePath());
        }

        try {
            ResourceFileUtil.unzip(new FileInputStream(clientZipFile), tempDir);
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException(clientZipFile.getAbsolutePath()
                    + " is not found.", e);
        }

        try {
            LogUtil.getLog().info(
                    "Creating " + dbfluteClientDir.getAbsolutePath());
            FileUtils.copyDirectory(new File(tempDir, "dbflute_dfclient"),
                    dbfluteClientDir);
        } catch (IOException e) {
            throw new MojoExecutionException("Could not create "
                    + dbfluteClientDir.getAbsolutePath(), e);
        }

        // Check schemaName
        if (StringUtils.isBlank(context.getSchemaName())) {
            LogUtil.getLog().info("Missing schemaName.");
            return;
        }

        // _project.sh
        Map<String, String> params = new HashMap<String, String>();
        putParam(params, "export MY_PROJECT_NAME=[^\r\n]+",
                "export MY_PROJECT_NAME=", context.getSchemaName());
        replaceContent(new File(context.getDbfluteClientDir(), "_project.sh"),
                params);

        // _project.bat
        params.clear();
        putParam(params, "set MY_PROJECT_NAME=[^\r\n]+",
                "set MY_PROJECT_NAME=", context.getSchemaName());
        replaceContent(new File(context.getDbfluteClientDir(), "_project.bat"),
                params);

        // build-dfclient.properties
        params.clear();
        putParam(params, "torque.project *= *[^\r\n]+", "torque.project = ",
                context.getSchemaName());
        // 0.8.x
        putParam(params, "torque.database *= *[^\r\n]+", "torque.database = ",
                context.getDatabase());
        putParam(params, "torque.packageBase *= *[^\r\n]+",
                "torque.packageBase = ", context.getDatabase());
        File propertyFile = new File(context.getDbfluteClientDir(),
                "build-dfclient.properties");
        replaceContent(propertyFile, params);
        propertyFile.renameTo(new File(context.getDbfluteClientDir(), "build-"
                + context.getSchemaName() + ".properties"));

        // dfprop/basicInfoMap.dfprop
        params.clear();
        putParam(params, "; database *= *[^\r\n]+", "; database = ", context
                .getDatabase());
        putParam(params, "; targetLanguage *= *[^\r\n]+",
                "; targetLanguage = ", context.getTargetLanguage());
        putParam(params, "; targetContainer *= *[^\r\n]+",
                "; targetContainer = ", context.getTargetContainer());
        putParam(params, "; packageBase *= *[^\r\n]+", "; packageBase = ",
                context.getDbPackage());
        replaceContent(new File(context.getDbfluteClientDir(),
                "dfprop/basicInfoMap.dfprop"), params);

        // dfprop/databaseInfoMap.dfprop
        params.clear();
        putParam(params, "; driver *= *[^\r\n]+", "; driver = ", context
                .getDatabaseDriver());
        putParam(params, "; url *= *[^\r\n]+", "; url = ", context
                .getDatabaseUrl());
        putParam(params, "; schema *= *[^\r\n]+", "; schema = ", context
                .getDatabaseSchema());
        putParam(params, "; user *= *[^\r\n]+", "; user = ", context
                .getDatabaseUser());
        putParam(params, "; password *= *[^\r\n]+", "; password = ", context
                .getDatabasePassword());
        replaceContent(new File(context.getDbfluteClientDir(),
                "dfprop/databaseInfoMap.dfprop"), params);

    }

    protected void putParam(Map<String, String> params, String key,
            String prefix, String value) {
        if (value != null) {
            params.put(key, prefix + value);
        }
    }

    protected void replaceContent(File file, Map<String, String> params)
            throws MojoExecutionException {
        if (!file.exists()) {
            LogUtil.getLog().info(
                    file.getAbsolutePath()
                            + " does not exists. Skip a content replacement.");
        }

        LogUtil.getLog()
                .info("Replacing contents in " + file.getAbsolutePath());
        Writer writer = null;
        try {
            String content = new String(FileUtil.getBytes(file), "UTF-8");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new MojoExecutionException("I/O error in "
                    + file.getAbsolutePath(), e);
        }
        IOUtils.closeQuietly(writer);
    }
}