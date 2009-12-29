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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.app.Velocity;
import org.seasar.dbflute.maven.plugin.entity.Column;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.entity.Database;
import org.seasar.dbflute.maven.plugin.entity.Table;
import org.seasar.dbflute.maven.plugin.util.TableMetaPropertiesUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shinsuke
 *
 */
public class CrudPropertyGenerator extends AbstractCrudGenerator {
    private File tableMetaProperties;

    public CrudPropertyGenerator(File schemaFile, File tableMetaProperties,
            DBFluteContext context) {
        this.schemaFile = schemaFile;
        this.context = context;
        this.tableMetaProperties = tableMetaProperties;
    }

    private String writeProperty(Writer writer, String keyPrefix, String name,
            String defaultValue) throws IOException {
        String key = keyPrefix + name;
        writer.write(key);
        writer.write("=");
        String value = TableMetaPropertiesUtil.getProperty(key);
        if (StringUtil.isNotEmpty(value)) {
            writer.write(value);
        } else {
            writer.write(defaultValue);
        }
        writer.write("\n");
        return value;
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

        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(
                    tableMetaProperties), "UTF-8");
            for (Table table : database.getTableList()) {
                String tableKeyPrefix = table.getPropertyName() + ".";
                String ignored = writeProperty(writer, tableKeyPrefix,
                        "ignored", "false");
                if (!"true".equalsIgnoreCase(ignored)) {
                    writeProperty(writer, tableKeyPrefix, "importPackages", "");
                    writeProperty(writer, tableKeyPrefix,
                            "converterToSearchParams", "");
                    writeProperty(writer, tableKeyPrefix, "converterToPager",
                            "");
                    writeProperty(writer, tableKeyPrefix,
                            "converterToActionForm", "");
                    writeProperty(writer, tableKeyPrefix, "converterToEntity",
                            "");
                    for (Column column : table.getColumnList()) {
                        String columnKeyPrefix = tableKeyPrefix
                                + column.getPropertyName() + ".";
                        writeProperty(writer, columnKeyPrefix, "requiredParam",
                                "");
                        writeProperty(writer, columnKeyPrefix,
                                "additionalAnnotation", "");
                        writeProperty(writer, columnKeyPrefix, "annotation", "");
                        writeProperty(writer, columnKeyPrefix,
                                "enableJavaType", "true");
                        writeProperty(writer, columnKeyPrefix, "longTypeParam",
                                "");
                        writeProperty(writer, columnKeyPrefix,
                                "integerTypeParam", "");
                        writeProperty(writer, columnKeyPrefix,
                                "shortTypeParam", "");
                        writeProperty(writer, columnKeyPrefix, "byteTypeParam",
                                "");
                        writeProperty(writer, columnKeyPrefix,
                                "floatTypeParam", "");
                        writeProperty(writer, columnKeyPrefix,
                                "doubleTypeParam", "");
                        writeProperty(writer, columnKeyPrefix, "dateTypeParam",
                                "");
                        writeProperty(writer, columnKeyPrefix, "defaultValue",
                                "");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Failed to write a table meta property.", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }
}
