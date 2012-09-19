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
package org.seasar.dbflute.maven.plugin;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.client.ClientCreator;
import org.seasar.dbflute.maven.plugin.util.LogUtil;

/**
 * CreateClientPlugin provides create-client goal to create dbflute client.
 * 
 * @goal create-client
 * 
 * @author shinsuke
 *
 */
public class CreateClientPlugin extends AbstractMojo {
    /**
     * @parameter expression="${dbflute.version}" 
     */
    protected String dbfluteVersion;

    /**
     * @parameter expression="${dbflute.downloadFilePrefix}" default-value="dbflute-"
     */
    protected String downloadFilePrefix;

    /**
     * @parameter expression="${dbflute.mydbfluteDir}" default-value="${basedir}/mydbflute"
     */
    protected File mydbfluteDir;

    /**
     * @parameter expression="${dbflute.dbfluteClientDir}" 
     */
    private File dbfluteClientDir;

    /**
     * @parameter expression="${dbflute.schemaName}"
     */
    protected String schemaName;

    /**
     * @parameter expression="${dbflute.enablePause}" default-value="false"
     */
    protected String enablePause;

    /**
     * @parameter expression="${dbflute.database}" default-value="h2"
     */
    protected String database;

    /**
     * @parameter expression="${dbflute.targetLanguage}" default-value="java"
     */
    protected String targetLanguage;

    /**
     * @parameter expression="${dbflute.targetContainer}" default-value="seasar"
     */
    protected String targetContainer;

    /**
     * @parameter expression="${dbflute.dbPackage}" default-value="${rootPackage}"
     */
    protected String dbPackage;

    /**
     * @parameter expression="${dbflute.databaseDriver}" default-value="org.h2.Driver"
     */
    protected String databaseDriver;

    // default-value="jdbc:h2:file:../src/main/webapp/WEB-INF/db/..."
    /**
     * @parameter expression="${dbflute.databaseUrl}"
     */
    protected String databaseUrl;

    /**
     * @parameter expression="${dbflute.databaseSchema}" default-value=" "
     */
    protected String databaseSchema;

    /**
     * @parameter expression="${dbflute.databaseUser}" default-value="sa"
     */
    protected String databaseUser;

    /**
     * @parameter expression="${dbflute.databasePassword}" default-value=" "
     */
    protected String databasePassword;

    private String dbfluteName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());

        if (StringUtils.isBlank(dbfluteVersion)) {
            throw new MojoFailureException("Missing dbfluteVersion property.");
        }

        dbfluteName = downloadFilePrefix + dbfluteVersion;

        ClientCreator creator = new ClientCreator(this);
        creator.execute();
    }

    public File getDbfluteDir() {
        return new File(mydbfluteDir, dbfluteName);
    }

    public File getDbfluteClientDir() {
        return dbfluteClientDir;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getEnablePause() {
        return enablePause;
    }

    public String getDbfluteName() {
        return dbfluteName;
    }

    public String getDatabase() {
        return database;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getTargetContainer() {
        return targetContainer;
    }

    public String getDbPackage() {
        return dbPackage;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public String getDatabaseUrl() {
        if (databaseUrl == null) {
            databaseUrl = "jdbc:h2:file:../src/main/webapp/WEB-INF/db/"
                    + schemaName;
        }
        return databaseUrl;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
