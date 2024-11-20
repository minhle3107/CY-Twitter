package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tweets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tweet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "type")
    private Integer type;

    @Column(name = "audience")
    private Integer audience;

    @Column(name = "content")
    private String content;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "user_views")
    private Integer userViews;

    @OneToMany(mappedBy = "tweet",fetch = FetchType.EAGER)
    List<TweetImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "tweet",fetch = FetchType.EAGER)
    List<TweetMention> mentions = new ArrayList<>();

    @OneToMany(mappedBy = "tweet",fetch = FetchType.EAGER)
    List<TweetHastag> hastags = new ArrayList<>();

}