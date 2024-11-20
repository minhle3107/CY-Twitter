package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tweet_hastags")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetHastag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @JoinColumn(name = "hastag_name")
    @ManyToOne
    private Hastag hastag;

}