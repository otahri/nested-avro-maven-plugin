package com.github.otahri;

import org.apache.avro.Schema;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkNotNull;

class NestedSchemas {

    static final String SCHEMA_NAME_CAN_T_BE_NULL = "Schema Name can't be null";
    private static final String SCHEMA_FILE_EXTENSION = ".avsc";
    private static final String UTF_8 = "UTF-8";
    static final String DIRECTORY_IS_EMPTY = "Directory is empty";
    private static final String ILLEGAL_SCHEMA = "Illegal Schema";
    private Queue<String> waitingQueue = new LinkedList<>();
    private Map<String, Schema> schemas = new HashMap<>();
    private static NestedSchemas nestedSchemas = new NestedSchemas();

    private NestedSchemas() {
    }

    static NestedSchemas getInstance() {
        return nestedSchemas;
    }

    void load(String inputDirectory) throws IOException, MojoExecutionException {
        if (Files.list(Paths.get(inputDirectory)).count() != 0) {
            Files
                    .list(Paths.get(inputDirectory))
                    .filter(Files::isRegularFile)
                    .filter(this::checkExtension)
                    .map(Path::toAbsolutePath)
                    .map(Path::toFile)
                    .map(this::convertFileToString)
                    .forEach(jsonFile -> waitingQueue.add(jsonFile));
        } else {
            throw new MojoExecutionException(DIRECTORY_IS_EMPTY);
        }
    }

    void compile() {
        while (!waitingQueue.isEmpty()) {
            String item = waitingQueue.poll();
            try {
                String completeSchema = compileSchema(item);
                Schema schema = new Schema.Parser().parse(completeSchema);
                String name = schema.getFullName();
                schemas.put(name, schema);
            } catch (RuntimeException e) {
                waitingQueue.offer(item);
            }
        }
    }

    void save(String outputDirectory, String schemaName, String fileName) throws IOException, MojoExecutionException {
        checkNotNull(schemaName, SCHEMA_NAME_CAN_T_BE_NULL);
        if (!schemas.isEmpty()) {
            try (FileWriter file = new FileWriter(outputDirectory + "/" + fileName)) {
                file.write(schemas.get(schemaName).toString(true));
            }
        } else {
            throw new MojoExecutionException(ILLEGAL_SCHEMA);
        }
    }

    private String convertFileToString(File file) {
        String str = null;
        try {
            str = FileUtils.readFileToString(file, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private String compileSchema(String schema) {
        for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
            schema = schema.replaceAll("\"" + entry.getKey() + "\"", entry.getValue().toString());
        }
        return schema;
    }

    private Boolean checkExtension(Path pathFile) {
        return pathFile.toString().endsWith(SCHEMA_FILE_EXTENSION);
    }
}
