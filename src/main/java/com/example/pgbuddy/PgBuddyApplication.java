package com.example.pgbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// enables spring to automatically populate certain fields in our entities (like createdDate, lastModifiedDate, createdBy, and lastModifiedBy) based on the lifecycle events of the entity.
// This is particularly useful for tracking when an entity was created or updated and by whom.
@EnableJpaAuditing
public class PgBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgBuddyApplication.class, args);
    }

}
