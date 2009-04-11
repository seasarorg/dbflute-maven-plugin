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

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.client.ClientCreator;
import org.seasar.dbflute.maven.plugin.entity.DBFluteContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.framework.beans.util.Beans;

/**
 * CreateClientPlugin provides create-client goal to create dbflute client.
 * 
 * @goal create-client
 * 
 * @author shinsuke
 *
 */
public class CreateClientPlugin extends AbstractDBFluteMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());

        if (StringUtils.isBlank(dbfluteVersion)) {
            throw new MojoFailureException("Missing dbfluteVersion property.");
        }

        String dbfluteName = downloadFilePrefix + dbfluteVersion;
        DBFluteContext context = Beans
                .createAndCopy(DBFluteContext.class, this).excludesNull()
                .execute();
        context.setDbfluteName(dbfluteName);

        ClientCreator creator = new ClientCreator(context);
        creator.execute();
    }

}
