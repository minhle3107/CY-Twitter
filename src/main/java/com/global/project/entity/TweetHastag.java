package com.global.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tweet_hastags")
public class TweetHastag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "hastag_id")
    private Long hastagId;

}