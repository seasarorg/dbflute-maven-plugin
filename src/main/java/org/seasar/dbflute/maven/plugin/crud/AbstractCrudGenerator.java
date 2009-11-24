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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.entity.Database;

/**
 * @author shinsuke
 *
 */
public abstract class AbstractCrudGenerator {

    protected File schemaFile;

    protected DBFluteContext context;

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