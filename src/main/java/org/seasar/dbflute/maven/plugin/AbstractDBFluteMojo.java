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

/**
 * AbstractDBFluteMojo is a parent Mojo class for maven-dbflute-plugin.
 * 
 * @author shinsuke
 *
 */
public abstract class AbstractDBFluteMojo extends AbstractMojo {

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
     * @parameter expression="${dbflute.resourceDir}" default-value="${basedir}/src/main/resources"
     */
    protected File resourceDir;

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
     * @parameter expression="${dbflute.dbfluteConfigDir}" default-value="${basedir}/dbflute"
     */
    protected File dbfluteConfigDir;

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public void setDbPackage(String dbPackage) {
        this.dbPackage = dbPackage;
    }

    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public void setActionPackageName(String actionPackageName) {
        this.actionPackageName = actionPackageName;
    }

    public void setFormPackageName(String formPackageName) {
        this.formPackageName = formPackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public void setPagerPackageName(String pagerPackageName) {
        this.pagerPackageName = pagerPackageName;
    }

    public void setSchemaFile(File schemaFile) {
        this.schemaFile = schemaFile;
    }

    public void setJavaDir(File javaDir) {
        this.javaDir = javaDir;
    }

    public void setJspDir(File jspDir) {
        this.jspDir = jspDir;
    }

    public void setResourceDir(File resourceDir) {
        this.resourceDir = resourceDir;
    }

    public void setMessagePropertyName(String messagePropertyName) {
        this.messagePropertyName = messagePropertyName;
    }

    public void setDbfluteVersion(String dbfluteVersion) {
        this.dbfluteVersion = dbfluteVersion;
    }

    public void setDownloadDirUrl(String downloadDirUrl) {
        this.downloadDirUrl = downloadDirUrl;
    }

    public void setDownloadFilePrefix(String downloadFilePrefix) {
        this.downloadFilePrefix = downloadFilePrefix;
    }

    public void setDownloadFileExtension(String downloadFileExtension) {
        this.downloadFileExtension = downloadFileExtension;
    }

    public void setMydbfluteDir(File mydbfluteDir) {
        this.mydbfluteDir = mydbfluteDir;
    }

    public void setDbfluteConfigDir(File dbfluteConfigDir) {
        this.dbfluteConfigDir = dbfluteConfigDir;
    }

}