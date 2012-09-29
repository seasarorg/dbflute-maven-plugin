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
import org.seasar.dbflute.maven.plugin.crud.CrudGenerator;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.dbflute.maven.plugin.util.TableMetaPropertiesUtil;
import org.seasar.util.lang.StringUtil;

/**
 * GenerateCrudPlugin provides generate-crud goal to generate CRUD pages.
 * 
 * @goal generate-crud
 * 
 * @execute phase="generate-resources"
 * 
 * @author shinsuke
 * 
 */
public class GenerateCrudPlugin extends AbstractMojo {

    /**
     * @parameter expression="${dbflute.tableMetaProperties}" default-value="${basedir}/src/main/config/tablemeta.properties"
     */
    protected File tableMetaProperties;

    /**
     * @parameter expression="${dbflute.schemaFile}"
     */
    protected File schemaFile;

    /**
     * @parameter expression="${dbflute.viewPrefix}" default-value=""
     */
    protected String viewPrefix;

    /**
     * @parameter expression="${dbflute.basePackageName}" default-value="crud"
     */
    protected String basePackageName;

    /**
     * @parameter expression="${dbflute.rootPackage}"
     */
    protected String rootPackage;

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
     * @parameter expression="${dbflute.javaDir}" default-value="${basedir}/src/main/java"
     */
    protected File javaDir;

    /**
     * @parameter expression="${dbflute.jspDir}" default-value="${basedir}/src/main/webapp/WEB-INF/view"
     */
    protected File jspDir;

    /**
     * @parameter expression="${dbflute.messagePropertyName}" default-value="application"
     */
    protected String messagePropertyName;

    /**
     * @parameter expression="${dbflute.resourcesDir}" default-value="${basedir}/src/main/resources"
     */
    protected File resourcesDir;

    /**
     * @parameter expression="${dbflute.locales}" default-value="ja"
     */
    protected String locales;

    /**
     * @parameter expression="${dbflute.dbPackage}" default-value="${rootPackage}"
     */
    protected String dbPackage;

    private String basePackage;

    private String actionPackage;

    private String baseActionPackage;

    private String formPackage;

    private String baseFormPackage;

    private String servicePackage;

    private String baseServicePackage;

    private String pagerPackage;

    private String basePagerPackage;

    private File rootJavaDir;

    private File baseJavaDir;

    private File actionJavaDir;

    private File formJavaDir;

    private File serviceJavaDir;

    private File pagerJavaDir;

    private File baseActionJavaDir;

    private File baseFormJavaDir;

    private File baseServiceJavaDir;

    private File basePagerJavaDir;

    private File rootJspDir;

    private String[] supportedLocales = new String[] { "", "ja" };

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());
        TableMetaPropertiesUtil.init(tableMetaProperties);

        if (StringUtil.isNotBlank(locales)) {
            supportedLocales = locales.split(",");
        }

        buildCrudPackage();
        CrudGenerator generator = new CrudGenerator(schemaFile, this);
        generator.execute();
    }

    private void buildCrudPackage() {
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

    private String getFileSeparator() {
        if (File.separator.equals("\\")) {
            return "\\\\";
        }
        return File.separator;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getActionPackage() {
        return actionPackage;
    }

    public String getBaseActionPackage() {
        return baseActionPackage;
    }

    public String getFormPackage() {
        return formPackage;
    }

    public String getBaseFormPackage() {
        return baseFormPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public String getBaseServicePackage() {
        return baseServicePackage;
    }

    public String getPagerPackage() {
        return pagerPackage;
    }

    public String getBasePagerPackage() {
        return basePagerPackage;
    }

    public File getRootJavaDir() {
        return rootJavaDir;
    }

    public File getBaseJavaDir() {
        return baseJavaDir;
    }

    public File getActionJavaDir() {
        return actionJavaDir;
    }

    public File getFormJavaDir() {
        return formJavaDir;
    }

    public File getServiceJavaDir() {
        return serviceJavaDir;
    }

    public File getPagerJavaDir() {
        return pagerJavaDir;
    }

    public File getBaseActionJavaDir() {
        return baseActionJavaDir;
    }

    public File getBaseFormJavaDir() {
        return baseFormJavaDir;
    }

    public File getBaseServiceJavaDir() {
        return baseServiceJavaDir;
    }

    public File getBasePagerJavaDir() {
        return basePagerJavaDir;
    }

    public File getRootJspDir() {
        return rootJspDir;
    }

    public File getJspDir() {
        return jspDir;
    }

    public String getMessagePropertyName() {
        return messagePropertyName;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public String[] getSupportedLocales() {
        return supportedLocales;
    }

    public String getDbPackage() {
        return dbPackage;
    }
}
