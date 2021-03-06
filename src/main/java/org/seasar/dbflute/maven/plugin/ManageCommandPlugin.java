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

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.command.CommandExecutor;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.util.lang.StringUtil;

/**
 * ManageCommandPlugin provides manage goal to run manage.[sh|bat].
 * 
 * @goal manage
 * 
 * @author shinsuke
 *
 */
public class ManageCommandPlugin extends CommandPlugin {

    /**
     * @parameter expression="${dbflute.manageTask}"
     */
    protected String manageTask;

    /**
     * @parameter expression="${dbflute.refreshProjects}"
     */
    protected String refreshProjects;

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());

        if (StringUtil.isNotBlank(refreshProjects)
                && !"refresh".equals(manageTask)) {
            throw new MojoFailureException(
                    "refreshProjects property is available only if manageTask property is refresh.");
        }

        CommandExecutor creator = new CommandExecutor(this);
        creator.execute("manage");
    }

    /**
     * @param cmds
     */
    @Override
    public void updateArgs(List<String> cmds) {
        if (StringUtil.isNotBlank(manageTask)) {
            cmds.add(manageTask);
            if ("refresh".equals(manageTask)
                    && StringUtil.isNotBlank(refreshProjects)) {
                cmds.add(refreshProjects);
            }
        }
    }

}
