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
import org.seasar.dbflute.maven.plugin.download.DBFluteDownloader;
import org.seasar.dbflute.maven.plugin.entity.DownloadContext;
import org.seasar.dbflute.maven.plugin.util.LogUtil;

/**
 * Download Plugin provides download goal to download a zip file of dbflute.
 * 
 * @author shinsuke
 * 
 * @goal download
 * 
 * @execute phase="generate-resources"
 * 
 * @author shinsuke
 *
 */
public class DownloadPlugin extends AbstractDBFluteMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        LogUtil.init(getLog());

        if (StringUtils.isBlank(dbfluteVersion)) {
            throw new MojoFailureException("Missing dbfluteVersion property.");
        }

        String dbfluteName = downloadFilePrefix + dbfluteVersion;
        String downloadPath = downloadDirUrl + dbfluteName
                + downloadFileExtension;
        DownloadContext context = new DownloadContext();
        context.setDbfluteName(dbfluteName);
        context.setDownloadPath(downloadPath);
        context.setMydbfluteDir(mydbfluteDir);

        DBFluteDownloader downloader = new DBFluteDownloader(context);
        downloader.execute();
    }

}
