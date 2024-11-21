package com.global.project.mapper;

import com.global.project.entity.Follower;
import com.global.project.entity.Notification;
import com.global.project.enums.NotificationStatus;
import com.global.project.modal.FollowRequest;
import com.global.project.modal.NotificationRequest;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationRequest notificationRequest){
        return Notification.builder()
                .username(notificationRequest.getRecipient())
                .actor(notificationRequest.getActor())
                .content(notificationRequest.getContent())
                .status(NotificationStatus.unseen)
                .build();
    }
}
