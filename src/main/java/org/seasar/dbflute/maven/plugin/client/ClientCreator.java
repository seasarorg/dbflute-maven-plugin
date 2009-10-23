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
import java.io.FilenameFilter;
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
        LogUtil.getLog().info("Unzip " + clientZipFile.getAbsolutePath());
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
            throw new MojoFailureException("Missing schemaName.");
        }

        Map<String, String> params = new HashMap<String, String>();
        if ("false".equalsIgnoreCase(context.getEnablePause())) {
            putParam(params, "pause", "rem ", "pause");
            for (File batFile : dbfluteClientDir
                    .listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".bat");
                        }
                    })) {
                ResourceFileUtil.replaceContent(batFile, params);
            }
        }

        // _project.sh
        params.clear();
        putParam(params, "export MY_PROJECT_NAME=[^\r\n]+",
                "export MY_PROJECT_NAME=", context.getSchemaName());
        putParam(params, "export DBFLUTE_HOME=../mydbflute/[^\r\n]+",
                "export DBFLUTE_HOME=../mydbflute/", context.getDbfluteName());
        ResourceFileUtil.replaceContent(new File(dbfluteClientDir,
                "_project.sh"), params);

        // _project.bat
        params.clear();
        putParam(params, "set MY_PROJECT_NAME=[^\r\n]+",
                "set MY_PROJECT_NAME=", context.getSchemaName());
        putParam(params, "set DBFLUTE_HOME=..\\\\mydbflute\\\\[^\r\n]+",
                "set DBFLUTE_HOME=..\\\\mydbflute\\\\", context
                        .getDbfluteName());
        ResourceFileUtil.replaceContent(new File(dbfluteClientDir,
                "_project.bat"), params);

        // build-dfclient.properties
        params.clear();
        putParam(params, "torque.project *= *[^\r\n]+", "torque.project = ",
                context.getSchemaName());
        // 0.8.x
        putParam(params, "torque.database *= *[^\r\n]+", "torque.database = ",
                context.getDatabase());
        putParam(params, "torque.packageBase *= *[^\r\n]+",
                "torque.packageBase = ", context.getDatabase());
        File propertyFile = new File(dbfluteClientDir,
                "build-dfclient.properties");
        if (!propertyFile.exists()) {
            // from 0.9.5.5            
            propertyFile = new File(dbfluteClientDir, "build.properties");
            ResourceFileUtil.replaceContent(propertyFile, params);
            propertyFile
                    .renameTo(new File(dbfluteClientDir, "build.properties"));
        } else {
            ResourceFileUtil.replaceContent(propertyFile, params);
            propertyFile.renameTo(new File(dbfluteClientDir, "build-"
                    + context.getSchemaName() + ".properties"));
        }

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
        ResourceFileUtil.replaceContent(new File(dbfluteClientDir,
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
        ResourceFileUtil.replaceContent(new File(dbfluteClientDir,
                "dfprop/databaseInfoMap.dfprop"), params);

    }

    protected void putParam(Map<String, String> params, String key,
            String prefix, String value) {
        if (value != null) {
            params.put(key, prefix + value);
        }
    }
}
