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
@Table(name = "tweet_images")
public class TweetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

}