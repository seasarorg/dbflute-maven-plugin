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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.seasar.framework.util.StringUtil;

/**
 * AbstractDBFluteMojo is a parent Mojo class for maven-dbflute-plugin.
 * 
 * @author shinsuke
 *
 */
public abstract class AbstractDBFluteMojo extends AbstractMojo {
    /**
     * Project base directory (prepended for relative file paths).
     *
     * @parameter expression="${basedir}"
     * @required
     */
    private File basedir;

    /**
     * The current Maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${dbflute.schemaName}"
     */
    protected String schemaName;

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

    /**
     * @parameter expression="${dbflute.rootPackage}"
     */
    protected String rootPackage;

    /**
     * @parameter expression="${dbflute.dbPackage}" default-value="${rootPackage}"
     */
    protected String dbPackage;

    /**
     * @parameter expression="${dbflute.viewPrefix}" default-value=""
     */
    protected String viewPrefix;

    /**
     * @parameter expression="${dbflute.basePackageName}" default-value="crud"
     */
    protected String basePackageName;

    /**
     * @parameter expression="${dbflute.actionPackageName}" default-value="action"
     */
    protected String actionPackageName;

    /**
     * @parameter expression="${dbflute.formPackageName}" default-value="form"
     */
    protected String formPackageName;

    /**
     * @parameter expression="${dbflute.servicePackageName}" default-value="service"
     */
    protected String servicePackageName;

    /**
     * @parameter expression="${dbflute.pagerPackageName}" default-value="pager"
     */
    protected String pagerPackageName;

    /**
     * @parameter expression="${dbflute.schemaFile}"
     */
    protected File schemaFile;

    /**
     * @parameter expression="${dbflute.javaDir}" default-value="${basedir}/src/main/java"
     */
    protected File javaDir;

    /**
     * @parameter expression="${dbflute.jspDir}" default-value="${basedir}/src/main/webapp/WEB-INF/view"
     */
    protected File jspDir;

    /**
     * @parameter expression="${dbflute.resourcesDir}" default-value="${basedir}/src/main/resources"
     */
    protected File resourcesDir;

    /**
     * @parameter expression="${dbflute.messagePropertyName}" default-value="application"
     */
    protected String messagePropertyName;

    /**
     * @parameter expression="${dbflute.version}" 
     */
    protected String dbfluteVersion;

    /**
     * @parameter expression="${dbflute.downloadDirUrl}" default-value="http://dbflute.sandbox.seasar.org/download/dbflute/"
     */
    protected String downloadDirUrl;

    /**
     * @parameter expression="${dbflute.downloadFilePrefix}" default-value="dbflute-"
     */
    protected String downloadFilePrefix;

    /**
     * @parameter expression="${dbflute.downloadFileExtension}" default-value=".zip"
     */
    protected String downloadFileExtension;

    /**
     * @parameter expression="${dbflute.mydbfluteDir}" default-value="${basedir}/mydbflute"
     */
    protected File mydbfluteDir;

    /**
     * @parameter expression="${dbflute.dbfluteClientDir}" default-value="${basedir}/dbflute_${project.artifactId}"
     */
    private File dbfluteClientDir;

    /**
     * @parameter expression="${dbflute.enablePause}" default-value="false"
     */
    protected String enablePause;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getTargetContainer() {
        return targetContainer;
    }

    public void setTargetContainer(String targetContainer) {
        this.targetContainer = targetContainer;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public String getDbPackage() {
        return dbPackage;
    }

    public void setDbPackage(String dbPackage) {
        this.dbPackage = dbPackage;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getActionPackageName() {
        return actionPackageName;
    }

    public void setActionPackageName(String actionPackageName) {
        this.actionPackageName = actionPackageName;
    }

    public String getFormPackageName() {
        return formPackageName;
    }

    public void setFormPackageName(String formPackageName) {
        this.formPackageName = formPackageName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getPagerPackageName() {
        return pagerPackageName;
    }

    public void setPagerPackageName(String pagerPackageName) {
        this.pagerPackageName = pagerPackageName;
    }

    public File getSchemaFile() {
        return schemaFile;
    }

    public void setSchemaFile(File schemaFile) {
        this.schemaFile = schemaFile;
    }

    public File getJavaDir() {
        return javaDir;
    }

    public void setJavaDir(File javaDir) {
        this.javaDir = javaDir;
    }

    public File getJspDir() {
        return jspDir;
    }

    public void setJspDir(File jspDir) {
        this.jspDir = jspDir;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourceDir) {
        this.resourcesDir = resourceDir;
    }

    public String getMessagePropertyName() {
        return messagePropertyName;
    }

    public void setMessagePropertyName(String messagePropertyName) {
        this.messagePropertyName = messagePropertyName;
    }

    public String getDbfluteVersion() {
        return dbfluteVersion;
    }

    public void setDbfluteVersion(String dbfluteVersion) {
        this.dbfluteVersion = dbfluteVersion;
    }

    public String getDownloadDirUrl() {
        return downloadDirUrl;
    }

    public void setDownloadDirUrl(String downloadDirUrl) {
        this.downloadDirUrl = downloadDirUrl;
    }

    public String getDownloadFilePrefix() {
        return downloadFilePrefix;
    }

    public void setDownloadFilePrefix(String downloadFilePrefix) {
        this.downloadFilePrefix = downloadFilePrefix;
    }

    public String getDownloadFileExtension() {
        return downloadFileExtension;
    }

    public void setDownloadFileExtension(String downloadFileExtension) {
        this.downloadFileExtension = downloadFileExtension;
    }

    public File getMydbfluteDir() {
        return mydbfluteDir;
    }

    public void setMydbfluteDir(File mydbfluteDir) {
        this.mydbfluteDir = mydbfluteDir;
    }

    public File getDbfluteClientDir() {
        if (dbfluteClientDir == null) {
            if (StringUtil.isBlank(schemaName)) {
                dbfluteClientDir = new File(basedir, "dbflute_"
                        + project.getArtifactId());
            } else {
                dbfluteClientDir = new File(basedir, "dbflute_" + schemaName);
            }
        }
        return dbfluteClientDir;
    }

    public void setDbfluteClientDir(File dbfluteClientDir) {
        this.dbfluteClientDir = dbfluteClientDir;
    }

    public File getBasedir() {
        return basedir;
    }

    public void setBasedir(File basedir) {
        this.basedir = basedir;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public String getEnablePause() {
        return enablePause;
    }

    public void setEnablePause(String enablePause) {
        this.enablePause = enablePause;
    }

}
