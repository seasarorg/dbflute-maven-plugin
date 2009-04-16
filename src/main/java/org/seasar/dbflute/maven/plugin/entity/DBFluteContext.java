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
package org.seasar.dbflute.maven.plugin.entity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * DownloadContext is a data holder to download a zip file.
 * 
 * @author shinsuke
 *
 */
public class DBFluteContext {

    private File javaDir;

    private File jspDir;

    private File resourcesDir;

    private String rootPackage;

    private File rootJavaDir;

    private String basePackage;

    private File baseJavaDir;

    private String actionPackage;

    private File actionJavaDir;

    private String formPackage;

    private File formJavaDir;

    private String servicePackage;

    private File serviceJavaDir;

    private String pagerPackage;

    private File pagerJavaDir;

    private String baseActionPackage;

    private File baseActionJavaDir;

    private String baseFormPackage;

    private File baseFormJavaDir;

    private String baseServicePackage;

    private File baseServiceJavaDir;

    private String basePagerPackage;

    private File basePagerJavaDir;

    private String dbPackage;

    private String viewPrefix;

    private String messagePropertyName;

    private File rootJspDir;

    private String basePackageName;

    private String actionPackageName;

    private String formPackageName;

    private String servicePackageName;

    private String pagerPackageName;

    private String[] supportedLocales = new String[] { "", "ja" };

    private File dbfluteClientDir;

    private File mydbfluteDir;

    private String dbfluteName;

    private String downloadPath;

    private String schemaName;

    private String database;

    private String targetLanguage;

    private String targetContainer;

    private String databaseDriver;

    private String databaseUrl;

    private String databaseSchema;

    private String databaseUser;

    private String databasePassword;

    public InputStream getDownloadInputStream() throws MojoExecutionException {
        try {
            URL url = new URL(downloadPath);
            return url.openStream();
        } catch (IOException e) {
            throw new MojoExecutionException("Could not open a connection of "
                    + downloadPath, e);
        }
    }

    public File getDbfluteDir() {
        return new File(mydbfluteDir, dbfluteName);
    }

    private String getFileSeparator() {
        if (File.separator.equals("\\")) {
            return "\\\\";
        }
        return File.separator;
    }

    public void buildCrudPackage() {
        if (StringUtils.isNotBlank(viewPrefix)) {
            viewPrefix = viewPrefix.replaceAll(getFileSeparator() + "+", "/")
                    .replaceAll("/+", "/").replaceAll("/$", "");
        } else {
            viewPrefix = "";
        }

        // build package name
        basePackage = rootPackage + "." + basePackageName;
        actionPackage = rootPackage + "." + actionPackageName;
        baseActionPackage = basePackage + "." + actionPackageName;
        formPackage = rootPackage + "." + formPackageName;
        baseFormPackage = basePackage + "." + formPackageName;
        if (StringUtils.isNotBlank(viewPrefix)) {
            String viewPackageName = viewPrefix.replaceAll("/", ".");
            actionPackage = actionPackage + "." + viewPackageName;
            baseActionPackage = baseActionPackage + "." + viewPackageName;
            formPackage = formPackage + "." + viewPackageName;
            baseFormPackage = baseFormPackage + "." + viewPackageName;
        }
        servicePackage = rootPackage + "." + servicePackageName;
        baseServicePackage = basePackage + "." + servicePackageName;
        pagerPackage = rootPackage + "." + pagerPackageName;
        basePagerPackage = basePackage + "." + pagerPackageName;

        // build File
        rootJavaDir = new File(javaDir, rootPackage.replaceAll("\\.",
                getFileSeparator()));
        baseJavaDir = new File(javaDir, basePackage.replaceAll("\\.",
                getFileSeparator()));
        actionJavaDir = new File(javaDir, actionPackage.replaceAll("\\.",
                getFileSeparator()));
        formJavaDir = new File(javaDir, formPackage.replaceAll("\\.",
                getFileSeparator()));
        serviceJavaDir = new File(javaDir, servicePackage.replaceAll("\\.",
                getFileSeparator()));
        pagerJavaDir = new File(javaDir, pagerPackage.replaceAll("\\.",
                getFileSeparator()));
        baseActionJavaDir = new File(javaDir, baseActionPackage.replaceAll(
                "\\.", getFileSeparator()));
        baseFormJavaDir = new File(javaDir, baseFormPackage.replaceAll("\\.",
                getFileSeparator()));
        baseServiceJavaDir = new File(javaDir, baseServicePackage.replaceAll(
                "\\.", getFileSeparator()));
        basePagerJavaDir = new File(javaDir, basePagerPackage.replaceAll("\\.",
                getFileSeparator()));
        if (StringUtils.isNotBlank(viewPrefix)) {
            rootJspDir = new File(jspDir, viewPrefix.replaceAll("/",
                    getFileSeparator()));
        } else {
            rootJspDir = jspDir;
        }
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

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public File getRootJavaDir() {
        return rootJavaDir;
    }

    public void setRootJavaDir(File rootJavaDir) {
        this.rootJavaDir = rootJavaDir;
    }

    public String getActionPackage() {
        return actionPackage;
    }

    public void setActionPackage(String actionPackage) {
        this.actionPackage = actionPackage;
    }

    public File getActionJavaDir() {
        return actionJavaDir;
    }

    public void setActionJavaDir(File actionJavaDir) {
        this.actionJavaDir = actionJavaDir;
    }

    public String getFormPackage() {
        return formPackage;
    }

    public void setFormPackage(String formPackage) {
        this.formPackage = formPackage;
    }

    public File getFormJavaDir() {
        return formJavaDir;
    }

    public void setFormJavaDir(File formJavaDir) {
        this.formJavaDir = formJavaDir;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public File getServiceJavaDir() {
        return serviceJavaDir;
    }

    public void setServiceJavaDir(File serviceJavaDir) {
        this.serviceJavaDir = serviceJavaDir;
    }

    public String getPagerPackage() {
        return pagerPackage;
    }

    public void setPagerPackage(String pagerPackage) {
        this.pagerPackage = pagerPackage;
    }

    public File getPagerJavaDir() {
        return pagerJavaDir;
    }

    public void setPagerJavaDir(File pagerJavaDir) {
        this.pagerJavaDir = pagerJavaDir;
    }

    public String getBaseActionPackage() {
        return baseActionPackage;
    }

    public void setBaseActionPackage(String baseActionPackage) {
        this.baseActionPackage = baseActionPackage;
    }

    public File getBaseActionJavaDir() {
        return baseActionJavaDir;
    }

    public void setBaseActionJavaDir(File baseActionJavaDir) {
        this.baseActionJavaDir = baseActionJavaDir;
    }

    public String getBaseFormPackage() {
        return baseFormPackage;
    }

    public void setBaseFormPackage(String baseFormPackage) {
        this.baseFormPackage = baseFormPackage;
    }

    public File getBaseFormJavaDir() {
        return baseFormJavaDir;
    }

    public void setBaseFormJavaDir(File baseFormJavaDir) {
        this.baseFormJavaDir = baseFormJavaDir;
    }

    public String getBaseServicePackage() {
        return baseServicePackage;
    }

    public void setBaseServicePackage(String baseServicePackage) {
        this.baseServicePackage = baseServicePackage;
    }

    public File getBaseServiceJavaDir() {
        return baseServiceJavaDir;
    }

    public void setBaseServiceJavaDir(File baseServiceJavaDir) {
        this.baseServiceJavaDir = baseServiceJavaDir;
    }

    public String getBasePagerPackage() {
        return basePagerPackage;
    }

    public void setBasePagerPackage(String basePagerPackage) {
        this.basePagerPackage = basePagerPackage;
    }

    public File getBasePagerJavaDir() {
        return basePagerJavaDir;
    }

    public void setBasePagerJavaDir(File basePagerJavaDir) {
        this.basePagerJavaDir = basePagerJavaDir;
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

    public File getRootJspDir() {
        return rootJspDir;
    }

    public void setRootJspDir(File rootJspDir) {
        this.rootJspDir = rootJspDir;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public File getBaseJavaDir() {
        return baseJavaDir;
    }

    public void setBaseJavaDir(File baseJavaDir) {
        this.baseJavaDir = baseJavaDir;
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

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getMessagePropertyName() {
        return messagePropertyName;
    }

    public void setMessagePropertyName(String messagePropertyName) {
        this.messagePropertyName = messagePropertyName;
    }

    public String[] getSupportedLocales() {
        return supportedLocales;
    }

    public void setSupportedLocales(String[] supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

    public File getMydbfluteDir() {
        return mydbfluteDir;
    }

    public void setMydbfluteDir(File mydbfluteDir) {
        this.mydbfluteDir = mydbfluteDir;
    }

    public String getDbfluteName() {
        return dbfluteName;
    }

    public void setDbfluteName(String dbfluteName) {
        this.dbfluteName = dbfluteName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public File getDbfluteClientDir() {
        return dbfluteClientDir;
    }

    public void setDbfluteClientDir(File dbfluteClientDir) {
        this.dbfluteClientDir = dbfluteClientDir;
    }

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

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public void setDatabaseDriver(String databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public String getDatabaseUrl() {
        if (databaseUrl == null) {
            databaseUrl = "jdbc:h2:file:../src/main/webapp/WEB-INF/db/"
                    + schemaName;
        }
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
}
