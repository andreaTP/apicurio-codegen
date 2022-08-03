/*
 * Copyright 2022 Red Hat Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.codegen.cli;

import io.apicurio.hub.api.codegen.OpenApi2JaxRs;
import io.apicurio.hub.api.codegen.OpenApi2Quarkus;
import io.apicurio.hub.api.codegen.OpenApi2Thorntail;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

@Command(name = "apicurio-codegen", mixinStandardHelpOptions = true, helpCommand = true)
public class GenerateJavaSources implements Runnable {

    @Option(names = { "-s", "--spec" }, description = "The specification to be used", required = true)
    File spec = null;

    @Option(names = { "-o", "--output" }, description = "The ZIP archive to be generated", required = true)
    File out = null;

    @Option(names = { "--code-only" }, description = "Generates only the code and do not scaffold the entire project")
    boolean codeOnly = true;

    @Option(names = { "--reactive" }, description = "Generate the reactive version")
    boolean reactive = false;

    @Option(names = { "--artifact-id" }, description = "The generated artifact id")
    String artifactId = "generated-api";

    @Option(names = { "--group-id" }, description = "The generated group id")
    String groupId = "org.example.api";

    @Option(names = { "--java-package" }, description = "The target java package")
    String javaPackage = "org.example.api";

    enum Generator {
        JAX_RS,
        QUARKUS,
        THORNTAIL
    }

    @Option(names = { "--generator" }, description = "The generator to be used")
    Generator generatorType = Generator.JAX_RS;

    @Override
    public void run() {

        OpenApi2JaxRs.JaxRsProjectSettings settings = new OpenApi2JaxRs.JaxRsProjectSettings();
        settings.codeOnly = codeOnly;
        settings.reactive = reactive;
        settings.artifactId = artifactId;
        settings.groupId = groupId;
        settings.javaPackage = javaPackage;

        // TODO: should it be configurable?
        OpenApi2JaxRs generator = null;
        switch (generatorType) {
            case JAX_RS:
                generator = new OpenApi2JaxRs();
                break;
            case QUARKUS:
                generator = new OpenApi2Quarkus();
                break;
            case THORNTAIL:
                generator = new OpenApi2Thorntail();
                break;
        }
        generator.setSettings(settings);
        // TODO: check if this should be configurable
        generator.setUpdateOnly(false);
        try {
            generator.setOpenApiDocument(new FileInputStream(spec));

            ByteArrayOutputStream outputStream = generator.generate();

            FileUtils.writeByteArrayToFile(out, outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GenerateJavaSources()).execute(args);
        System.exit(exitCode);
    }
}
