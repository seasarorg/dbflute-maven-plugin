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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.crud.CrudPropertyGenerator;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.dbflute.maven.plugin.util.TableMetaPropertiesUtil;

/**
 * GenerateCrudPlugin provides generate-crud goal to generate CRUD pages.
 * 
 * @goal generate-crud-tablemeta
 * 
 * @execute phase="generate-resources"
 * 
 * @author shinsuke
 * 
 */
public class GenerateCrudPropertyPlugin extends AbstractMojo {

    /**
     * @parameter expression="${dbflute.tableMetaProperties}" default-value="${basedir}/src/main/config/tablemeta.properties"
     */
    protected File tableMetaProperties;

    /**
     * @parameter expression="${dbflute.schemaFile}"
     */
    protected File schemaFile;

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());
        TableMetaPropertiesUtil.init(tableMetaProperties);

        CrudPropertyGenerator generator = new CrudPropertyGenerator(schemaFile,
                tableMetaProperties, this);
        generator.execute();
    }

}
