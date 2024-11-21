package com.global.project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {
    private Long id;

    private String senderUsername;

    private String receiveUsername;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
