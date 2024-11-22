package com.global.project.mapper;

import com.global.project.dto.NotificationResponse;
import com.global.project.entity.Notification;
import com.global.project.enums.NotificationStatus;
import com.global.project.modal.NotificationRequest;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toResponse(Notification notification){
        return NotificationResponse.builder()
                .id(notification.getId())
                .username(notification.getUsername())
                .content(notification.getContent())
                .type(notification.getType())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .seenAt(notification.getSeenAt())
                .build();
    }
    public Notification toEntity(NotificationRequest notificationRequest){
        return Notification.builder()
                .username(notificationRequest.getRecipient())
                .actor(notificationRequest.getActor())
                .content(notificationRequest.getContent())
                .status(NotificationStatus.unseen)
                .build();
    }
}
