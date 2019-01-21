package org.avro;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo(name = "compileSchema")
public class CompileSchemaMojo extends AbstractMojo {

    @Parameter(property = "inputDirectory", defaultValue = "${project.basedir}/src/main/resources")
    String inputDirectory;
    @Parameter(property = "outputDirectory", defaultValue = "${project.basedir}/src/main/resources")
    String outputDirectory;
    @Parameter(property = "schemaName")
    String schemaName;
    @Parameter(property = "outputFileName", defaultValue = "Output.avsc")
    String outputFileName;

    public void execute() {

        NestedSchemas nestedSchemas = NestedSchemas.getInstance();
        try {
            nestedSchemas.load(inputDirectory);
            nestedSchemas.compile();
            nestedSchemas.save(outputDirectory, schemaName, outputFileName);
        } catch (IOException | MojoExecutionException e) {
            e.printStackTrace();
        }
    }
}
