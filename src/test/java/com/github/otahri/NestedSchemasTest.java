package com.github.otahri;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static com.github.otahri.NestedSchemas.DIRECTORY_IS_EMPTY;
import static com.github.otahri.NestedSchemas.SCHEMA_NAME_CAN_T_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class NestedSchemasTest {

    @Rule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static NestedSchemas nestedSchemas;

    @BeforeAll
    static void setUp() throws IOException {
        temporaryFolder.create();
        nestedSchemas = NestedSchemas.getInstance();
    }

    @Test
    void testEmptyDirectoryShouldThrowException() {
        Throwable exception = assertThrows(MojoExecutionException.class, () ->
                nestedSchemas.load(temporaryFolder.newFolder("input").getPath())
        );
        assertEquals(DIRECTORY_IS_EMPTY, exception.getMessage());
    }

    @Test
    void testNullSchemaShouldThrowException() throws IOException, MojoExecutionException {
        File inputDirectory = new File(this.getClass().getResource("/InputSchemas").getFile());
        nestedSchemas.load(inputDirectory.getAbsolutePath());
        nestedSchemas.compile();
        Throwable exception = assertThrows(NullPointerException.class, () ->
                nestedSchemas.save(temporaryFolder.newFolder("output").getPath(), null, "Output.avsc")
        );
        assertEquals(SCHEMA_NAME_CAN_T_BE_NULL, exception.getMessage());
    }

    @AfterAll
    static void close() {
        temporaryFolder.delete();
    }
}