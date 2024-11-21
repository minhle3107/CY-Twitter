package com.global.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {
    private Long id;
    private String senderUsername;
    private String receiveUsername;
    private String content;
    private LocalDateTime createdAt;
}