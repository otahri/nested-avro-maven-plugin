# nestedAvro-maven-plugin

[![Build Status](https://travis-ci.com/otahri/nestedAvro-maven-plugin.svg?branch=master)](https://travis-ci.com/otahri/nestedAvro-maven-plugin)

A maven plugin for creating complete Avro schema from multiple nested schema files.

* [Purpose](#purpose)
* [Setup](#setup)
* [Usage](#usage)
* [Example](#example)


## Purpose

## Setup

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.avro</groupId>
            <artifactId>nestedAvro-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <configuration>
                <inputDirectory></inputDir>
                <outputDirectory></outputDir>
                <schemaName></schemaName>
                <outputFileName></fileName>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Usage

```bash
$ mvn nestedAvro:compileSchema
```

## Example
