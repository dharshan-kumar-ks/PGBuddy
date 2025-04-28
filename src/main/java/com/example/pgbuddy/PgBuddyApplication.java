package com.example.pgbuddy;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
// enables spring to automatically populate certain fields in our entities (like createdDate, lastModifiedDate, createdBy, and lastModifiedBy) based on the lifecycle events of the entity.
// This is particularly useful for tracking when an entity was created or updated and by whom.
@EnableJpaAuditing
public class PgBuddyApplication {

    public static void main(String[] args) {
        // Load .env file before Spring starts
        //Dotenv dotenv = Dotenv.configure().load();
        // Convert Dotenv entries to Map and add to System properties
        //dotenv.entries().forEach(entry ->
        //        System.setProperty(entry.getKey(), entry.getValue())
        //);

        // Start the Spring application
        SpringApplication.run(PgBuddyApplication.class, args);
    }

//    @Bean
//    CommandLineRunner checkEnv() {
//        return args -> {
//            System.out.println("=== Environment Variables ===");
//            System.out.println("DATABASE_URL (System Property): " + System.getProperty("DATABASE_URL"));
//            System.out.println("DB_USERNAME (System Property): " + System.getProperty("DB_USERNAME"));
//            System.out.println("DATABASE_URL (Environment Variable): " + System.getenv("DATABASE_URL"));
//            System.out.println("DB_USERNAME (Environment Variable): " + System.getenv("DB_USERNAME"));
//        };
//    }

    // Check if the database connection is successful at application startup.
    @Bean
    CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            System.out.println("Testing database connection...");
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ Connection successful!");
                System.out.println("URL: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.err.println("❌ Connection failed: " + e.getMessage());
            }
        };
    }

}
