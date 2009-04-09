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

import org.apache.commons.lang.StringUtils;

/**
 * CrudContext is a data holder to generate CRUD files.
 * 
 * @author shinsuke
 *
 */
public class CrudContext {
    private File javaDir;

    private File jspDir;

    private File resourcesDir;

    private String rootPkg;

    private File rootJavaDir;

    private String basePkg;

    private File baseJavaDir;

    private String actionPkg;

    private File actionJavaDir;

    private String formPkg;

    private File formJavaDir;

    private String servicePkg;

    private File serviceJavaDir;

    private String pagerPkg;

    private File pagerJavaDir;

    private String baseActionPkg;

    private File baseActionJavaDir;

    private String baseFormPkg;

    private File baseFormJavaDir;

    private String baseServicePkg;

    private File baseServiceJavaDir;

    private String basePagerPkg;

    private File basePagerJavaDir;

    private String dbPkg;

    private String viewPrefix;

    private String propertyFileName;

    private File rootJspDir;

    private String basePkgName;

    private String actionPkgName;

    private String formPkgName;

    private String servicePkgName;

    private String pagerPkgName;

    private String[] supportedLocales = new String[] { "", "ja" };

    public CrudContext() {
    }

    public void init() {
        if (StringUtils.isNotBlank(viewPrefix)) {
            viewPrefix = viewPrefix.replaceAll(File.separator + "+", "/")
                    .replaceAll("/+", "/").replaceAll("/$", "");
        } else {
            viewPrefix = "";
        }

        // build package name
        basePkg = rootPkg + "." + basePkgName;
        actionPkg = rootPkg + "." + actionPkgName;
        baseActionPkg = basePkg + "." + actionPkgName;
        formPkg = rootPkg + "." + formPkgName;
        baseFormPkg = basePkg + "." + formPkgName;
        if (StringUtils.isNotBlank(viewPrefix)) {
            String viewPkgName = viewPrefix.replaceAll("/", ".");
            actionPkg = actionPkg + "." + viewPkgName;
            baseActionPkg = baseActionPkg + "." + viewPkgName;
            formPkg = formPkg + "." + viewPkgName;
            baseFormPkg = baseFormPkg + "." + viewPkgName;
        }
        servicePkg = rootPkg + "." + servicePkgName;
        baseServicePkg = basePkg + "." + servicePkgName;
        pagerPkg = rootPkg + "." + pagerPkgName;
        basePagerPkg = basePkg + "." + pagerPkgName;

        // build File
        rootJavaDir = new File(javaDir, rootPkg.replaceAll("\\.",
                File.separator));
        baseJavaDir = new File(javaDir, basePkg.replaceAll("\\.",
                File.separator));
        actionJavaDir = new File(javaDir, actionPkg.replaceAll("\\.",
                File.separator));
        formJavaDir = new File(javaDir, formPkg.replaceAll("\\.",
                File.separator));
        serviceJavaDir = new File(javaDir, servicePkg.replaceAll("\\.",
                File.separator));
        pagerJavaDir = new File(javaDir, pagerPkg.replaceAll("\\.",
                File.separator));
        baseActionJavaDir = new File(javaDir, baseActionPkg.replaceAll("\\.",
                File.separator));
        baseFormJavaDir = new File(javaDir, baseFormPkg.replaceAll("\\.",
                File.separator));
        baseServiceJavaDir = new File(javaDir, baseServicePkg.replaceAll("\\.",
                File.separator));
        basePagerJavaDir = new File(javaDir, basePagerPkg.replaceAll("\\.",
                File.separator));
        if (StringUtils.isNotBlank(viewPrefix)) {
            rootJspDir = new File(jspDir, viewPrefix.replaceAll("/",
                    File.separator));
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

    public String getRootPkg() {
        return rootPkg;
    }

    public void setRootPkg(String rootPkg) {
        this.rootPkg = rootPkg;
    }

    public File getRootJavaDir() {
        return rootJavaDir;
    }

    public void setRootJavaDir(File rootJavaDir) {
        this.rootJavaDir = rootJavaDir;
    }

    public String getActionPkg() {
        return actionPkg;
    }

    public void setActionPkg(String actionPkg) {
        this.actionPkg = actionPkg;
    }

    public File getActionJavaDir() {
        return actionJavaDir;
    }

    public void setActionJavaDir(File actionJavaDir) {
        this.actionJavaDir = actionJavaDir;
    }

    public String getFormPkg() {
        return formPkg;
    }

    public void setFormPkg(String formPkg) {
        this.formPkg = formPkg;
    }

    public File getFormJavaDir() {
        return formJavaDir;
    }

    public void setFormJavaDir(File formJavaDir) {
        this.formJavaDir = formJavaDir;
    }

    public String getServicePkg() {
        return servicePkg;
    }

    public void setServicePkg(String servicePkg) {
        this.servicePkg = servicePkg;
    }

    public File getServiceJavaDir() {
        return serviceJavaDir;
    }

    public void setServiceJavaDir(File serviceJavaDir) {
        this.serviceJavaDir = serviceJavaDir;
    }

    public String getPagerPkg() {
        return pagerPkg;
    }

    public void setPagerPkg(String pagerPkg) {
        this.pagerPkg = pagerPkg;
    }

    public File getPagerJavaDir() {
        return pagerJavaDir;
    }

    public void setPagerJavaDir(File pagerJavaDir) {
        this.pagerJavaDir = pagerJavaDir;
    }

    public String getBaseActionPkg() {
        return baseActionPkg;
    }

    public void setBaseActionPkg(String baseActionPkg) {
        this.baseActionPkg = baseActionPkg;
    }

    public File getBaseActionJavaDir() {
        return baseActionJavaDir;
    }

    public void setBaseActionJavaDir(File baseActionJavaDir) {
        this.baseActionJavaDir = baseActionJavaDir;
    }

    public String getBaseFormPkg() {
        return baseFormPkg;
    }

    public void setBaseFormPkg(String baseFormPkg) {
        this.baseFormPkg = baseFormPkg;
    }

    public File getBaseFormJavaDir() {
        return baseFormJavaDir;
    }

    public void setBaseFormJavaDir(File baseFormJavaDir) {
        this.baseFormJavaDir = baseFormJavaDir;
    }

    public String getBaseServicePkg() {
        return baseServicePkg;
    }

    public void setBaseServicePkg(String baseServicePkg) {
        this.baseServicePkg = baseServicePkg;
    }

    public File getBaseServiceJavaDir() {
        return baseServiceJavaDir;
    }

    public void setBaseServiceJavaDir(File baseServiceJavaDir) {
        this.baseServiceJavaDir = baseServiceJavaDir;
    }

    public String getBasePagerPkg() {
        return basePagerPkg;
    }

    public void setBasePagerPkg(String basePagerPkg) {
        this.basePagerPkg = basePagerPkg;
    }

    public File getBasePagerJavaDir() {
        return basePagerJavaDir;
    }

    public void setBasePagerJavaDir(File basePagerJavaDir) {
        this.basePagerJavaDir = basePagerJavaDir;
    }

    public String getDbPkg() {
        return dbPkg;
    }

    public void setDbPkg(String dbPkg) {
        this.dbPkg = dbPkg;
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

    public String getBasePkg() {
        return basePkg;
    }

    public void setBasePkg(String basePkg) {
        this.basePkg = basePkg;
    }

    public File getBaseJavaDir() {
        return baseJavaDir;
    }

    public void setBaseJavaDir(File baseJavaDir) {
        this.baseJavaDir = baseJavaDir;
    }

    public String getBasePkgName() {
        return basePkgName;
    }

    public void setBasePkgName(String basePkgName) {
        this.basePkgName = basePkgName;
    }

    public String getActionPkgName() {
        return actionPkgName;
    }

    public void setActionPkgName(String actionPkgName) {
        this.actionPkgName = actionPkgName;
    }

    public String getFormPkgName() {
        return formPkgName;
    }

    public void setFormPkgName(String formPkgName) {
        this.formPkgName = formPkgName;
    }

    public String getServicePkgName() {
        return servicePkgName;
    }

    public void setServicePkgName(String servicePkgName) {
        this.servicePkgName = servicePkgName;
    }

    public String getPagerPkgName() {
        return pagerPkgName;
    }

    public void setPagerPkgName(String pagerPkgName) {
        this.pagerPkgName = pagerPkgName;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getPropertyFileName() {
        return propertyFileName;
    }

    public void setPropertyFileName(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    public String[] getSupportedLocales() {
        return supportedLocales;
    }

    public void setSupportedLocales(String[] supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

}
