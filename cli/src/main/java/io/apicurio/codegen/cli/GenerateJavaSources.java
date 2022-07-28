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
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Command(name = "apicurio-codegen", mixinStandardHelpOptions = true, helpCommand = true)
// TODO: remove if un-necessary
@RegisterForReflection(targets = {})
public class GenerateJavaSources implements Runnable {

    @Option(names = { "-s", "--spec" }, description = "The specification to be used", required = true)
    URL spec = null;

    @Option(names = { "-o", "--output" }, description = "The ZIP archive to be generated", required = true)
    File out = null;

    @Override
    public void run() {

        OpenApi2JaxRs.JaxRsProjectSettings settings = new OpenApi2JaxRs.JaxRsProjectSettings();
        settings.codeOnly = false;
        settings.reactive = false;
        settings.artifactId = "generated-api";
        settings.groupId = "org.example.api";
        settings.javaPackage = "org.example.api";

        OpenApi2JaxRs generator = new OpenApi2JaxRs();
        generator.setSettings(settings);
        generator.setUpdateOnly(false);
        try {
            generator.setOpenApiDocument(spec);

            ByteArrayOutputStream outputStream = generator.generate();

            FileUtils.writeByteArrayToFile(out, outputStream.toByteArray());
            System.out.println("Generated ZIP can be found here: " + out.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GenerateJavaSources()).execute(args);
        System.exit(exitCode);
    }
}
