# nestedAvro-maven-plugin

[![Build Status](https://travis-ci.org/otahri/nestedAvro-maven-plugin.svg?branch=master)](https://travis-ci.org/otahri/nestedAvro-maven-plugin)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/otahri/nestedAvro-maven-plugin/master/LICENSE)

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
                <inputDirectory></inputDirectory>
                <outputDirectory></outputDirectory>
                <schemaName></schemaName>
                <outputFileName></outputFileName>
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
