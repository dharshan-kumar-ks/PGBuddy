package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
// used to define a class that can be inherited by entities but is not itself an entity.
// allows us to extract common attributes or behavior into a superclass, which are then inherited by subclasses that are entities.
// superclass itself is not mapped to a database table, but its attributes are included in the tables of its subclasses
@MappedSuperclass
// marking this class to listen to JPAAuditing -> the attributes with annotations like @CreatedDate & @LastModifiedDate will update its values based on the object lifecycle
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    // marks this class attribute as the primary key of the table
    @Id
    // indicates that the primary key should be automatically generated for each row by the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // specifies the details of the column in the database table
    @Column(name = "id", nullable = false) // nullable is 'false' by default
    private Long id;

    // @CreatedDate & @LastModifiedDate -> annotations are used with JPA auditing to automatically set the creation and modification timestamps when an entity is created or updated.
    @CreatedDate
    @Column(updatable = false) // Prevent updates after creation
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedAt;
}
