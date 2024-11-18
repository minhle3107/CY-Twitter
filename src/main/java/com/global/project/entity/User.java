package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Size(max = 255)
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Size(max = 255)
    @Column(name = "website")
    private String website;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 255)
    @Column(name = "cover_photo")
    private String coverPhoto;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "gender")
    private int gender;

}