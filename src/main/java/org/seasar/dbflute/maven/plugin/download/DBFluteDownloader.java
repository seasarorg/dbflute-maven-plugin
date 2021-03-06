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
package org.seasar.dbflute.maven.plugin.download;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.dbflute.maven.plugin.DownloadPlugin;
import org.seasar.dbflute.maven.plugin.util.LogUtil;
import org.seasar.dbflute.maven.plugin.util.ResourceFileUtil;

/**
 * DBFluteDownloader downloads dbflute-*.zip and extracts it.
 * 
 * @author shinsuke
 *
 */
public class DBFluteDownloader {
    protected DownloadPlugin plugin;

    public DBFluteDownloader(DownloadPlugin plugin) {
        this.plugin = plugin;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        File dbfluteDir = plugin.getDbfluteDir();
        if (!dbfluteDir.exists()) {
            LogUtil.getLog().info("Creating " + dbfluteDir.getAbsolutePath());
            ResourceFileUtil.unzip(plugin.getDownloadInputStream(), dbfluteDir);
        }

    }

}
