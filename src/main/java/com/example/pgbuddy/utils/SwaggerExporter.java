package com.example.pgbuddy.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// This class is responsible for exporting the Swagger API documentation from "http://localhost:8081/v3/api-docs"
@Component
public class SwaggerExporter implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            URL url = new URL("http://localhost:8081/v3/api-docs");
            String json = new String(url.openStream().readAllBytes(), StandardCharsets.UTF_8);

            Path output = Paths.get("src/main/resources/static/swagger.json");
            Files.writeString(output, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Swagger spec saved to " + output);
        } catch (Exception e) {
            System.err.println("Failed to fetch swagger.json: " + e.getMessage());
        }
    }
}

// Copy the generated swagger.json file to Downloads folder
// cp /path/to/your/project/src/main/resources/static/swagger.json ~/Downloads/
