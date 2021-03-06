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
package org.seasar.dbflute.maven.plugin.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.seasar.util.lang.StringUtil;

/**
 * @author shinsuke
 *
 */
public class TableMetaPropertiesUtil {
    private static Properties tableMetaProperties;

    public static String getProperty(String key) {
        String value = tableMetaProperties.getProperty(key);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return value;
    }

    public static void init(File file) throws MojoExecutionException {
        TableMetaPropertiesUtil.tableMetaProperties = new Properties();
        if (file != null && file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                tableMetaProperties.load(fis);
            } catch (Exception e) {
                throw new MojoExecutionException(file.getAbsolutePath()
                        + " is not found.", e);
            } finally {
                IOUtils.closeQuietly(fis);
            }
        }

    }
}
