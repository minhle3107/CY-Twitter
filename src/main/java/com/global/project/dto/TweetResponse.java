package com.global.project.dto;

import com.global.project.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    List<TweetImageResponse> images;

    List<TweetHastagResponse> hastags;

    List<TweetMentionResponse> mentions;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
