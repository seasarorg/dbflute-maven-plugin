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

import java.util.ArrayList;
import java.util.List;

import org.seasar.dbflute.maven.plugin.entity.Column;
import org.seasar.dbflute.maven.plugin.entity.Database;
import org.seasar.dbflute.maven.plugin.entity.Table;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * DBSchemaHandler parses project-schema-*.xml generated by DBFlute, 
 * and creates a database entity model.
 * 
 * @author shinsuke
 *
 */
public class DBSchemaHandler extends DefaultHandler {
    private Database database;

    private Table table;

    private Column column;

    private List<String> stack;

    @Override
    public void startDocument() throws SAXException {
        stack = new ArrayList<String>();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        stack.add(name);
        if ("database".equals(name)) {
            database = new Database();
            String nameAttr = attributes.getValue("name");
            if (nameAttr != null) {
                database.setName(nameAttr);
            }
        } else if ("table".equals(name)) {
            table = new Table();
            String nameAttr = attributes.getValue("name");
            if (nameAttr != null) {
                table.setName(nameAttr);
            }
            String typeAttr = attributes.getValue("type");
            if (typeAttr != null) {
                table.setType(typeAttr);
            }
        } else if ("column".equals(name)) {
            column = new Column();
            String autoIncrementAttr = attributes.getValue("autoIncrement");
            if (autoIncrementAttr != null) {
                column.setAutoIncrement("true".equals(autoIncrementAttr));
            }
            String commentAttr = attributes.getValue("comment");
            if (commentAttr != null) {
                column.setComment(commentAttr);
            }
            String dbTypeAttr = attributes.getValue("dbType");
            if (dbTypeAttr != null) {
                column.setDbType(dbTypeAttr);
            }
            String javaTypeAttr = attributes.getValue("javaType");
            if (javaTypeAttr != null) {
                column.setJavaType(javaTypeAttr);
            }
            String nameAttr = attributes.getValue("name");
            if (nameAttr != null) {
                column.setName(nameAttr);
            }
            String primaryKeyAttr = attributes.getValue("primaryKey");
            if (primaryKeyAttr != null) {
                column.setPrimaryKey("true".equals(primaryKeyAttr));
            }
            String requiredAttr = attributes.getValue("required");
            if (requiredAttr != null) {
                column.setRequired("true".equals(requiredAttr));
            }
            String typeAttr = attributes.getValue("type");
            if (typeAttr != null) {
                column.setType(typeAttr);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        if ("database".equals(name)) {
        } else if ("table".equals(name)) {
            database.addTable(table);
            table = null;
        } else if ("column".equals(name)) {
            table.addColumn(column);
            column = null;
        }

        stack.remove(stack.size() - 1);
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
    }

    public Database getDatabase() {
        return database;
    }

}
