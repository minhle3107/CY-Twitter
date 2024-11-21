package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.NotificationRequest;
import org.springframework.http.ResponseEntity;

public interface INotificationService {
    ResponseEntity<ApiResponse<?>> createNotification(NotificationRequest notificationRequest);
    void pushNotification(String recipient, NotificationRequest notificationRequest);
}
