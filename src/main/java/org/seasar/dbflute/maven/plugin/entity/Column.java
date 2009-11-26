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

import java.io.Serializable;

import org.seasar.dbflute.maven.plugin.util.TableMetaPropertiesUtil;
import org.seasar.framework.util.StringUtil;

/**
 * Column is an entity class for column tag in a project schema file.
 * 
 * @author shinsuke
 *
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean autoIncrement;

    private String comment;

    private String dbType;

    private String javaType;

    private String name;

    private boolean primaryKey;

    private boolean required;

    private String type;

    private String propertyName;

    private String methodName;

    private Table table;

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (name != null) {
            methodName = StringUtil.camelize(name);
            propertyName = StringUtil.decapitalize(methodName);
        }
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    // meta properties

    private String getColumnName() {
        return table.getPropertyName() + "." + getPropertyName();
    }

    public String getAnnotation() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".annotation");
    }

    public String getRequiredParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".requiredParam");
    }

    public boolean isEnableJavaType() {
        String value = TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".enableJavaType");
        if (value != null && "false".equalsIgnoreCase(value)) {
            return false;
        }
        return true;
    }

    public String getLongTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".longTypeParam");
    }

    public String getIntegerTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".integerTypeParam");
    }

    public String getShortTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".shortTypeParam");
    }

    public String getByteTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".byteTypeParam");
    }

    public String getFloatTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".floatTypeParam");
    }

    public String getDoubleTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".doubleTypeParam");
    }

    public String getDateTypeParam() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".dateTypeParam");
    }

    public String getDefaultValue() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".defaultValue");
    }

    public String getAdditionalAnnotation() {
        return TableMetaPropertiesUtil.getProperty(getColumnName()
                + ".additionalAnnotation");
    }
}
