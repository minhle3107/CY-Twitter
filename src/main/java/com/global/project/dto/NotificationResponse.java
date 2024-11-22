package com.global.project.dto;

import com.global.project.enums.NotificationStatus;
import com.global.project.enums.TypeStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String username;
    private TypeStatus type;
    private String content;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime seenAt;
}
