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
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Column(name = "type")
    private Integer type;

    @Column(name = "audience")
    private Integer audience;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "user_views")
    private Integer userViews;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}