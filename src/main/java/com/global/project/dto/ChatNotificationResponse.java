package com.global.project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotificationResponse {
    private Long id;
    private String senderUsername;
    private String recipientUsername;
    private String content;
}
