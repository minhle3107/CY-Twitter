package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity  {
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

    private LocalDate dob;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 255)
    @Column(name = "cover_photo")
    private String coverPhoto;


    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "gender")
    private int gender;

}