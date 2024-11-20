package com.global.project.dto;

import com.global.project.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetResponse {
    private Long id;

    private User user;

    private Integer type;

    private Integer audience;

    private String content;

    private Long parentId;

    private Integer userViews;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
