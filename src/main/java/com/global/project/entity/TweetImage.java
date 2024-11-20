package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tweet_images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

}