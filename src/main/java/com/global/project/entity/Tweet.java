package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

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

}