package com.example.pgbuddy.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Model class for Notice details
@Getter
@Setter
@Entity
@Table(name = "notices")
public class Notice extends BaseModel {
    // Multiple Notice can only have 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    // the author_id column in the notices table will be used to join with the primary key of the users table
    // in the table "author_id" column will be present and not the entire user object for it
    @JoinColumn(name = "author_id", unique = false)
    private User author;

    private String title;
    private String content;
    //private String createdAtDay;
    //private String createdAtTime;
    private boolean bookmarked;

    // Multiple Users can have same Notice & users can bookmark multiple notices (M:M)
    @ManyToMany(mappedBy = "bookmarkedNotices")
    private Set<User> usersWhoBookmarked = new HashSet<>();

    // createdAt - this is already defined in BaseModel
}
