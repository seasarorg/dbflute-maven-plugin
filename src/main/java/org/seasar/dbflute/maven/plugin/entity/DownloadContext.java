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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * DownloadContext is a data holder to download a zip file.
 * 
 * @author shinsuke
 *
 */
public class DownloadContext {

    protected File mydbfluteDir;

    protected String dbfluteName;

    protected String downloadPath;

    public InputStream getDownloadInputStream() throws MojoExecutionException {
        try {
            URL url = new URL(downloadPath);
            return url.openStream();
        } catch (IOException e) {
            throw new MojoExecutionException("Could not open a connection of "
                    + downloadPath, e);
        }
    }

    public File getDbfluteDir() {
        return new File(mydbfluteDir, dbfluteName);
    }

    public File getMydbfluteDir() {
        return mydbfluteDir;
    }

    public void setMydbfluteDir(File mydbfluteDir) {
        this.mydbfluteDir = mydbfluteDir;
    }

    public String getDbfluteName() {
        return dbfluteName;
    }

    public void setDbfluteName(String dbfluteName) {
        this.dbfluteName = dbfluteName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
