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

// This class is responsible for exporting the Swagger API documentation from "http://localhost:8081/v3/api-docs" to a local file "swagger.json"
// This class listens for the ApplicationReadyEvent to trigger the export of the Swagger documentation - meaning the documentation is exported during app startup
@Component
public class SwaggerExporter implements ApplicationListener<ApplicationReadyEvent> {

    // This method is called when the springboot application is ready
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            // Attempt to fetch the Swagger JSON from the first URL (belonging to Render hosted app)
            String json;
            try {
                URL url = new URL("https://pgbuddy.onrender.com/v3/api-docs");
                json = new String(url.openStream().readAllBytes(), StandardCharsets.UTF_8); // Read the JSON from the URL
            } catch (Exception e) {
                // Fallback to the second URL (app running on local server on PC) if the first one fails
                System.err.println("Failed to fetch from localhost URL, trying fallback: " + e.getMessage());
                URL fallbackUrl = new URL("http://localhost:8081/v3/api-docs");
                json = new String(fallbackUrl.openStream().readAllBytes(), StandardCharsets.UTF_8); // Read the JSON from the fallback URL
            }

            // Write the fetched JSON to the output file "swagger.json"
            Path output = Paths.get("src/main/resources/static/swagger.json");
            // Create the file if it doesn't exist, and overwrite it if it does
            Files.writeString(output, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Swagger spec saved to " + output); // Print the path where the file is saved
        } catch (Exception e) {
            System.err.println("Failed to fetch swagger.json: " + e.getMessage()); // Print the error message if fetching fails
        }
    }
}

// Copy the generated swagger.json file to Downloads folder - useful for debugging when the app is running on local server :-
// cp /path/to/your/project/src/main/resources/static/swagger.json ~/Downloads/
