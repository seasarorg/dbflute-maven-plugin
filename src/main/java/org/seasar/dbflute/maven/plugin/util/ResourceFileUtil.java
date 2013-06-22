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
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.seasar.util.io.FileUtil;

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

    public static File createTempDir(String prefix, String suffix)
            throws MojoExecutionException, MojoFailureException {
        // create temp dir
        File tempDir = null;
        try {
            tempDir = File.createTempFile("dbflute-client", "");
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Could not create a temp directory. ", e);
        }
        if (!tempDir.delete()) {
            throw new MojoFailureException(
                    "Could not create a temp directory: "
                            + tempDir.getAbsolutePath());
        }
        return tempDir;
    }

    public static void unzip(InputStream inputStream, File destDir)
            throws MojoFailureException, MojoExecutionException {
        unzip(inputStream, destDir, true);
    }

    public static void unzip(InputStream inputStream, File destDir,
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

                    File targetFile = new File(destDir + File.separator
                            + entryName);
                    makeDir(targetFile);
                } else {
                    LogUtil.getLog().info("Extracting " + entryName);

                    File targetFile = new File(destDir + File.separator
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

    public static void replaceContent(File file, Map<String, String> params)
            throws MojoExecutionException {
        replaceContent(file, params, false);
    }

    public static void replaceContent(File file, Map<String, String> params,
            boolean replacedPause) throws MojoExecutionException {
        if (!file.exists()) {
            LogUtil.getLog().info(
                    file.getAbsolutePath()
                            + " does not exists. Skip a content replacement.");
        }

        LogUtil.getLog()
                .info("Replacing contents in " + file.getAbsolutePath());
        Writer writer = null;
        try {
            String content = FileUtil.readText(file, "UTF-8");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }
            if (replacedPause) {
                int pos = content.indexOf("pause_at_end");
                if (pos == -1) {
                    content = content
                            .replaceAll("pause\r\n",
                                    "if \"%pause_at_end%\"==\"y\" (\r\n  pause\r\n)\r\n");
                    if ("_project.bat".equals(file.getName())) {
                        content = content
                                + "\r\n\r\nif \"%pause_at_end%\"==\"\" set pause_at_end=y\r\n";
                    }
                }
            }
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new MojoExecutionException("I/O error in "
                    + file.getAbsolutePath(), e);
        }
        IOUtils.closeQuietly(writer);
    }

  
}
