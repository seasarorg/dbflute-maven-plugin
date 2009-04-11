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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * A utility class to handling a resource.
 * 
 * @author shinsuke
 *
 */
public class ResourceFileUtil {

    public static void makeDir(File dir) throws MojoFailureException {
        if (dir.isDirectory()) {
            return;
        }
        LogUtil.getLog().info("Creating " + dir.getAbsolutePath());
        if (!dir.mkdirs()) {
            throw new MojoFailureException("Could not create "
                    + dir.getAbsolutePath());
        }
    }

    public static void unzip(InputStream inputStream, File unzipDbfluteDir)
            throws MojoFailureException, MojoExecutionException {
        unzip(inputStream, unzipDbfluteDir, true);
    }

    public static void unzip(InputStream inputStream, File unzipDbfluteDir,
            boolean overwrite) throws MojoFailureException,
            MojoExecutionException {

        ZipInputStream in = new ZipInputStream(new BufferedInputStream(
                inputStream));

        ZipEntry zipEntry = null;

        try {
            while ((zipEntry = in.getNextEntry()) != null) {
                String entryName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    LogUtil.getLog().info("Extracting " + entryName);

                    File targetFile = new File(unzipDbfluteDir + File.separator
                            + entryName);
                    makeDir(targetFile);
                } else {
                    LogUtil.getLog().info("Extracting " + entryName);

                    File targetFile = new File(unzipDbfluteDir + File.separator
                            + entryName);
                    makeDir(targetFile.getParentFile());

                    if (overwrite || !targetFile.exists()) {
                        BufferedOutputStream out = null;
                        try {
                            out = new BufferedOutputStream(
                                    new FileOutputStream(targetFile));

                            int data = 0;
                            while ((data = in.read()) != -1) {
                                out.write(data);
                            }

                            out.flush();
                        } catch (IOException e) {
                            throw new MojoExecutionException(
                                    "Could not extract " + entryName, e);
                        } finally {
                            IOUtils.closeQuietly(out);
                            try {
                                in.closeEntry();
                            } catch (IOException e) {
                                // NOP
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Could not extract downloaded file.", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
