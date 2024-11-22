package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.NotificationResponse;
import com.global.project.modal.NotificationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INotificationService {
    ResponseEntity<ApiResponse<?>> createNotification(NotificationRequest notificationRequest);
    void pushNotification(String recipient, NotificationRequest notificationRequest);
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByUserName();
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByStatusSeen();
    ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByStatusUnSeen();
}
