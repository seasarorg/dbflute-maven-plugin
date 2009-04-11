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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Table is an entity class for table tag in a project schema file.
 * 
 * @author shinsuke
 *
 */
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Column> columnList;

    private String name;

    private String type;

    private String className;

    private String propertyName;

    public Table() {
        columnList = new ArrayList<Column>();
    }

    public String getPrimaryKeyPath() {
        StringBuilder buf = new StringBuilder();
        for (Column column : columnList) {
            if (column.isPrimaryKey()) {
                if (buf.length() > 0) {
                    buf.append("/").append(column.getPropertyName());
                } else {
                    buf.append(column.getPropertyName());
                }
            }
        }
        return buf.toString();
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public void addColumn(Column column) {
        this.columnList.add(column);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (name != null) {
            String[] values = name.split("_");
            StringBuilder cbuf = new StringBuilder();
            StringBuilder pbuf = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == 0) {
                    cbuf.append(StringUtils.capitalize(values[i]));
                    pbuf.append(StringUtils.uncapitalize(values[i]));
                } else {
                    cbuf.append(StringUtils.capitalize(values[i]));
                    pbuf.append(StringUtils.capitalize(values[i]));
                }
            }
            className = cbuf.toString();
            propertyName = pbuf.toString();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public String getPropertyName() {
        return propertyName;
    }
}