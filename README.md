DBFlute Maven Plugin
====================

## Overview

DBFlute Maven Plugin allows you to configure files and run commands for DBFlute on mvn command.

## Features

DBFlute Maven Plugin provides the follwing goals:

* download: Downloads and unzips DBFlute file you specified.
* create-client: Unzips dbflute_dfclient.zip in the above file, and configures parameters.
* upgrade: Downloads and unzips a new DBFlute file, and updates _project.[sh|bat].
* jdbc: Runs jdbc.[sh|bat].
* generate: Runs generate.[sh|bat].
* doc: Runs doc.[sh|bat].
* outside-sql-test: Runs outside-sql-test.[sh|bat].
* replace-schema: Runs replace-schema.[sh|bat].
* sql2entity: Runs sql2entity.[sh|bat].
* manage: Runs manage.[sh|bat].
* generate-crud: Generates CRUD files of SAStruts for each table.
* generate-crud-tablemeta: Generate properties file which includes parameters for generate-crud goal.

## Usage

Goals of DBFlute Maven Plugin are used as mvn sub-commands. 
To use this plugin, add the following code to pom.xml of your project.

    <?xml version="1.0" encoding="UTF-8"?>
    <project>
    ...
      <pluginRepositories>
        <pluginRepository>
          <id>maven.seasar.org</id>
          <name>The Seasar Foundation Maven2 Repository</name>
          <url>http://maven.seasar.org/maven2/</url>
        </pluginRepository>
        <pluginRepository>
          <id>maven-snapshot.seasar.org</id>
          <name>The Seasar Foundation Maven2 Repository</name>
          <url>http://maven.seasar.org/maven2-snapshot/</url>
        </pluginRepository>
      </pluginRepositories>
    ...
      <build>
        <plugins>
    ...
          <plugin>
            <groupId>org.seasar.dbflute</groupId>
            <artifactId>dbflute-maven-plugin</artifactId>
            <version>1.0.1</version>
            <configuration>
              <dbfluteVersion>1.0.4K</dbfluteVersion>
              <rootPackage>sample.app</rootPackage>
              <dbPackage>sample.app.db</dbPackage>
              <clientProject>sample</clientProject>
              <schemaFile>${basedir}/dbflute/schema/project-schema-sample.xml</schemaFile>
            </configuration>
          </plugin>
    ...
        </plugins>
      </build>
    ...
    </project>

For example, you can run download goal below:

    mvn dbflute:download

