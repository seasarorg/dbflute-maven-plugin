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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.crud.CrudGenerator;
import org.seasar.dbflute.maven.plugin.entity.CrudContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;

/**
 * GenerateCrudPlugin provides generate-crud goal to generate CRUD pages.
 * 
 * @author shinsuke
 * 
 * @goal generate-crud
 * 
 * @execute phase="generate-resources"
 */
public class GenerateCrudPlugin extends AbstractDBFluteMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());

        CrudContext crudContext = new CrudContext();
        crudContext.setJavaDir(javaDir);
        crudContext.setJspDir(jspDir);
        crudContext.setResourcesDir(resourceDir);
        crudContext.setRootPkg(rootPackage);
        crudContext.setDbPkg(dbPackage);
        crudContext.setViewPrefix(viewPrefix);
        crudContext.setPropertyFileName(messagePropertyName);
        crudContext.setBasePkgName(basePackageName);
        crudContext.setActionPkgName(actionPackageName);
        crudContext.setFormPkgName(formPackageName);
        crudContext.setServicePkgName(servicePackageName);
        crudContext.setPagerPkgName(pagerPackageName);
        crudContext.init();
        CrudGenerator generator = new CrudGenerator(schemaFile, crudContext);
        generator.execute();
    }

}
