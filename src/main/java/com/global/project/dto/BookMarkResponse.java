package com.global.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMarkResponse {
    private Long id;

    private Long tweetId;

    private String username;

    private LocalDateTime createdAt;
}
