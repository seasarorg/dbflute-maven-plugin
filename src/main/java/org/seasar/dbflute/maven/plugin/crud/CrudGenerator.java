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
package org.seasar.dbflute.maven.plugin.crud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.seasar.dbflute.maven.plugin.entity.CrudContext;
import org.seasar.dbflute.maven.plugin.entity.Database;
import org.seasar.dbflute.maven.plugin.entity.Table;
import org.seasar.dbflute.maven.plugin.util.ResourceUtil;

/**
 * CrudGenerator generates .java and .jsp files for CRUD pages.
 * 
 * @author shinsuke
 *
 */
public class CrudGenerator {
    private static final String TEMPLATE_JAVA_PATH = "template/sastruts/java/";

    private static final String TEMPLATE_RESOURCE_PATH = "template/sastruts/resources/";

    private static final String TEMPLATE_JSP_PATH = "template/sastruts/webapp/WEB-INF/view/";

    private File schemaFile;

    private CrudContext crudContext;

    public CrudGenerator(File schemaFile, CrudContext crudContext) {
        this.schemaFile = schemaFile;
        this.crudContext = crudContext;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!schemaFile.isFile()) {
            throw new MojoFailureException("The schema file does not exist: "
                    + schemaFile.getAbsolutePath());
        }

        try {
            Properties props = new Properties();
            props.setProperty("resource.loader", "CLASS");
            props
                    .setProperty("CLASS.resource.loader.class",
                            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(props);
        } catch (Exception e) {
            throw new MojoExecutionException("init() of Velocity failed.", e);
        }

        Database database = getDatabaseModel();

        // create dir
        ResourceUtil.makeDir(crudContext.getActionJavaDir());
        ResourceUtil.makeDir(crudContext.getBaseActionJavaDir());
        ResourceUtil.makeDir(crudContext.getBaseFormJavaDir());
        ResourceUtil.makeDir(crudContext.getBasePagerJavaDir());
        ResourceUtil.makeDir(crudContext.getBaseServiceJavaDir());
        ResourceUtil.makeDir(crudContext.getFormJavaDir());
        ResourceUtil.makeDir(crudContext.getPagerJavaDir());
        ResourceUtil.makeDir(crudContext.getServiceJavaDir());
        File utilJavaDir = new File(crudContext.getBaseJavaDir(), "util");
        ResourceUtil.makeDir(utilJavaDir);
        File creatorJavaDir = new File(crudContext.getBaseJavaDir(), "creator");
        ResourceUtil.makeDir(crudContext.getJspDir());

        // action/CrudTableAction.java
        for (Table table : database.getTableList()) {
            String className = table.getClassName() + "Action";
            File classFile = new File(crudContext.getActionJavaDir(), className
                    + ".java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "action/CrudTableAction.vm", params);
            }
        }

        // action/IndexAction.java
        {
            File classFile = new File(crudContext.getActionJavaDir(),
                    "IndexAction.java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "action/IndexAction.vm", params);
            }
        }

        // base/CommonConstants.java
        {
            File classFile = new File(crudContext.getBaseJavaDir(),
                    "CommonConstants.java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "base/CommonConstants.vm", params);
            }
        }

        // base/util/SAStrutsUtil.java
        {
            File classFile = new File(utilJavaDir, "SAStrutsUtil.java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "base/util/SAStrutsUtil.vm", params);
            }
        }

        // base/action/BsCrudTableAction.java
        for (Table table : database.getTableList()) {
            String className = "Bs" + table.getClassName() + "Action";
            File classFile = new File(crudContext.getBaseActionJavaDir(),
                    className + ".java");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("crudContext", crudContext);
            params.put("database", database);
            params.put("table", table);
            createFile(classFile, TEMPLATE_JAVA_PATH
                    + "base/action/BsCrudTableAction.vm", params);
        }

        // base/pager/BsCrudTablePager.java
        for (Table table : database.getTableList()) {
            String className = "Bs" + table.getClassName() + "Pager";
            File classFile = new File(crudContext.getBasePagerJavaDir(),
                    className + ".java");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("crudContext", crudContext);
            params.put("database", database);
            params.put("table", table);
            createFile(classFile, TEMPLATE_JAVA_PATH
                    + "base/pager/BsCrudTablePager.vm", params);
        }

        // base/form/BsCrudTableForm.java
        for (Table table : database.getTableList()) {
            String className = "Bs" + table.getClassName() + "Form";
            File classFile = new File(crudContext.getBaseFormJavaDir(),
                    className + ".java");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("crudContext", crudContext);
            params.put("database", database);
            params.put("table", table);
            createFile(classFile, TEMPLATE_JAVA_PATH
                    + "base/form/BsCrudTableForm.vm", params);
        }

        // base/service/BsCrudTableService.java
        for (Table table : database.getTableList()) {
            String className = "Bs" + table.getClassName() + "Service";
            File classFile = new File(crudContext.getBaseServiceJavaDir(),
                    className + ".java");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("crudContext", crudContext);
            params.put("database", database);
            params.put("table", table);
            createFile(classFile, TEMPLATE_JAVA_PATH
                    + "base/service/BsCrudTableService.vm", params);
        }

        // pager/CrudTablePager.java
        for (Table table : database.getTableList()) {
            String className = table.getClassName() + "Pager";
            File classFile = new File(crudContext.getPagerJavaDir(), className
                    + ".java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "pager/CrudTablePager.vm", params);
            }
        }

        // form/CrudTableForm.java
        for (Table table : database.getTableList()) {
            String className = table.getClassName() + "Form";
            File classFile = new File(crudContext.getFormJavaDir(), className
                    + ".java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "form/CrudTableForm.vm", params);
            }
        }

        // base/creator/PagerCreator.java
        {
            File classFile = new File(creatorJavaDir, "PagerCreator.java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "base/creator/PagerCreator.vm", params);
            }
        }

        // service/CrudTableService.java
        for (Table table : database.getTableList()) {
            String className = table.getClassName() + "Service";
            File classFile = new File(crudContext.getServiceJavaDir(),
                    className + ".java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "service/CrudTableService.vm", params);
            }
        }

        // baseCrudMessageException.java
        {
            File classFile = new File(crudContext.getBaseJavaDir(),
                    "CrudMessageException.java");
            if (!classFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(classFile, TEMPLATE_JAVA_PATH
                        + "base/CrudMessageException.vm", params);
            }
        }

        for (Table table : database.getTableList()) {
            String propertyName = table.getPropertyName();
            File jspDir = new File(crudContext.getJspDir(), propertyName);
            ResourceUtil.makeDir(jspDir);

            // crudTable/confirm.vm
            File confirmJspFile = new File(jspDir, "confirm.jsp");
            if (!confirmJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(confirmJspFile, TEMPLATE_JSP_PATH
                        + "crudTable/confirm.vm", params);
            }

            // crudTable/edit.vm
            File editJspFile = new File(jspDir, "edit.jsp");
            if (!editJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(editJspFile,
                        TEMPLATE_JSP_PATH + "crudTable/edit.vm", params);
            }

            // crudTable/error.vm
            File errorJspFile = new File(jspDir, "error.jsp");
            if (!errorJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(errorJspFile, TEMPLATE_JSP_PATH
                        + "crudTable/error.vm", params);
            }

            // crudTable/index.vm
            File indexJspFile = new File(jspDir, "index.jsp");
            if (!indexJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                params.put("table", table);
                createFile(indexJspFile, TEMPLATE_JSP_PATH
                        + "crudTable/index.vm", params);
            }
        }

        // common/common.vm
        {
            File jspCommonDir = new File(crudContext.getJspDir(), "common");
            ResourceUtil.makeDir(jspCommonDir);

            File commonJspFile = new File(jspCommonDir, "common.jsp");
            if (!commonJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(commonJspFile, TEMPLATE_JSP_PATH
                        + "common/common.vm", params);
            }
        }

        // index.vm
        {
            File indexJspFile = new File(crudContext.getJspDir(), "index.jsp");
            if (!indexJspFile.exists()) {
                // create
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("crudContext", crudContext);
                params.put("database", database);
                createFile(indexJspFile, TEMPLATE_JSP_PATH + "index.vm", params);
            }
        }

        createMessagePropertyFile();
    }

    protected void createMessagePropertyFile() throws MojoFailureException,
            MojoExecutionException {
        String[] values = crudContext.getPropertyFileName().split("\\.");
        File propDir = crudContext.getResourcesDir();
        for (int i = 0; i < values.length - 1; i++) {
            propDir = new File(propDir, values[i]);
        }
        String propName = values[values.length - 1];

        for (String localeStr : crudContext.getSupportedLocales()) {
            String propFileName = null;
            String basePropFileName = null;
            if (StringUtils.isEmpty(localeStr)) {
                propFileName = propName + ".properties";
                basePropFileName = "application.properties";
            } else {
                propFileName = propName + "_" + localeStr + ".properties";
                basePropFileName = "application_" + localeStr + ".properties";
            }

            InputStream is = ResourceUtil
                    .getResourceAsStream(TEMPLATE_RESOURCE_PATH
                            + basePropFileName);

            File propFile = new File(propDir, propFileName);

            File tempFile = null;
            BufferedReader br = null;
            BufferedWriter bw = null;
            BufferedReader bbr = null;
            try {
                tempFile = File.createTempFile(propName, ".properties");
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(propFile), "UTF-8"));
                bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(tempFile), "UTF-8"));
                bbr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                boolean isWrite = true;
                while ((line = br.readLine()) != null) {
                    if (line.contains("# CRUD PROPERTIES: BEGIN")) {
                        isWrite = false;
                    } else if (line.contains("# CRUD PROPERTIES: END")) {
                        isWrite = true;
                    }
                    if (isWrite) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                bw.newLine();
                bw.flush();

                while ((line = bbr.readLine()) != null) {
                    bw.write(line);
                    bw.newLine();
                }
                bw.flush();
            } catch (IOException e) {
                throw new MojoExecutionException("Could not generate "
                        + propFile.getAbsolutePath(), e);
            } finally {
                IOUtils.closeQuietly(br);
                IOUtils.closeQuietly(bbr);
                IOUtils.closeQuietly(bw);
            }

            if (tempFile == null) {
                throw new MojoFailureException("Could not generate "
                        + propFile.getAbsolutePath());
            }
            try {
                FileUtils.copyFile(tempFile, propFile);
            } catch (IOException e) {
                throw new MojoExecutionException("Could not generate "
                        + propFile.getAbsolutePath(), e);
            }
        }
    }

    protected void createFile(File targetFile, String templatePath,
            Map<String, Object> params) throws MojoExecutionException {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(targetFile),
                    "UTF-8");

            VelocityContext context = new VelocityContext();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }

            if (!templatePath.startsWith("/")) {
                templatePath = "/" + templatePath;
            }

            Template template = Velocity.getTemplate(templatePath, "UTF-8");
            template.merge(context, writer);
            writer.flush();
        } catch (Exception e) {
            throw new MojoExecutionException("Could not generate "
                    + targetFile.getAbsolutePath(), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    protected Database getDatabaseModel() throws MojoExecutionException,
            MojoFailureException {
        DBSchemaHandler handler = new DBSchemaHandler();
        SAXParserFactory spfactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = spfactory.newSAXParser();
            parser.parse(schemaFile, handler);
            return handler.getDatabase();
        } catch (Exception e) {
            throw new MojoExecutionException(
                    "Could not create Database instance.", e);
        }
    }
}
