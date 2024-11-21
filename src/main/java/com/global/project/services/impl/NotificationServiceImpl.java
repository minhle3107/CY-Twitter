package com.global.project.services.impl;

import com.global.project.dto.ApiResponse;
import com.global.project.entity.Notification;
import com.global.project.enums.NotificationStatus;
import com.global.project.enums.TypeStatus;
import com.global.project.mapper.NotificationMapper;
import com.global.project.modal.NotificationRequest;
import com.global.project.repository.INotificationRepository;
import com.global.project.services.INotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements INotificationService {
    private  NotificationMapper _mapper;
    private  INotificationRepository _repository;
    private  SimpMessagingTemplate _messagingTemplate;

    public NotificationServiceImpl(INotificationRepository repository,
                                   NotificationMapper mapper,
                                   SimpMessagingTemplate messagingTemplate) {
        _messagingTemplate = messagingTemplate;
        _repository = repository;
        _mapper = mapper;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> createNotification(NotificationRequest notificationRequest) {
        Notification notification = _mapper.toEntity(notificationRequest);
        try {
            notification.setCreatedAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.unseen);
            _repository.save(notification);
            pushNotification(notification.getUsername(), notificationRequest);

            return ResponseEntity.ok(ApiResponse.builder().message("Notification created successfully").build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.builder().message("Error creating notification: " + e.getMessage()).build());
        }
    }

    @Override
    public void pushNotification(String recipient, NotificationRequest notificationRequest) {
        _messagingTemplate.convertAndSendToUser(
                recipient,
                "/queue/notifications",
                notificationRequest
        );
    }
}

