package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.NotificationResponse;
import com.global.project.dto.UserResponse;
import com.global.project.entity.Notification;
import com.global.project.enums.NotificationStatus;
import com.global.project.enums.TypeStatus;
import com.global.project.mapper.NotificationMapper;
import com.global.project.modal.NotificationRequest;
import com.global.project.repository.INotificationRepository;
import com.global.project.services.INotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements INotificationService {
    private final NotificationMapper _mapper;
    private final INotificationRepository _repository;
    private final SimpMessagingTemplate _messagingTemplate;
    private final JwtProvider _jwtProvider;

    public NotificationServiceImpl(INotificationRepository repository,
                                   NotificationMapper mapper,
                                   SimpMessagingTemplate messagingTemplate,
                                   JwtProvider jwtProvider) {
        _messagingTemplate = messagingTemplate;
        _repository = repository;
        _mapper = mapper;
        _jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> createNotification(NotificationRequest notificationRequest) {
        Notification notification = _mapper.toEntity(notificationRequest);
        try {
            notification.setCreatedAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.unseen);
            notification.setType(notificationRequest.getType());
            _repository.save(notification);


            return ResponseEntity.ok(ApiResponse.builder().message("Notification created successfully").build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.builder().message("Error creating notification: " + e.getMessage()).build());
        }
    }

    @Override
    public void pushNotification(String recipient, NotificationRequest notificationRequest) {
        try {
            // Gửi tin nhắn tới user cụ thể qua STOMP
            _messagingTemplate.convertAndSendToUser(
                    recipient,
                    "/notifications", // Đích đến
                    notificationRequest     // Payload
            );
        } catch (Exception e) {
            System.out.println("Error sending notification: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByUserName() {
        try {
            String userName = _jwtProvider.getUsernameContext();
            List<Notification> notifications = _repository.getNotificationByUsername(userName);
            List<NotificationResponse> notificationResponses = notifications.stream()
                    .map(notification -> _mapper.toResponse(notification))
                    .toList();
            ApiResponse<List<NotificationResponse>> apiResponse = ApiResponse.<List<NotificationResponse>>builder()
                    .message("Notifications retrieved successfully.")
                    .data(notificationResponses)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<List<NotificationResponse>>builder()
                            .message("Error retrieving notifications: " + e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByStatusSeen() {
        try {
            String userName = _jwtProvider.getUsernameContext();
            List<Notification> notifications = _repository.findByUsernameAndStatus(userName,NotificationStatus.seen);
            List<NotificationResponse> notificationResponses = notifications.stream()
                    .map(notification -> _mapper.toResponse(notification))
                    .toList();
            ApiResponse<List<NotificationResponse>> apiResponse = ApiResponse.<List<NotificationResponse>>builder()
                    .message("Notifications retrieved successfully.")
                    .data(notificationResponses)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<List<NotificationResponse>>builder()
                            .message("Error retrieving notifications: " + e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationByStatusUnSeen() {
        try {
            String userName = _jwtProvider.getUsernameContext();
            List<Notification> notifications = _repository.findByUsernameAndStatus(userName,NotificationStatus.unseen);
            List<NotificationResponse> notificationResponses = notifications.stream()
                    .map(notification -> _mapper.toResponse(notification))
                    .toList();
            ApiResponse<List<NotificationResponse>> apiResponse = ApiResponse.<List<NotificationResponse>>builder()
                    .message("Notifications retrieved successfully.")
                    .data(notificationResponses)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<List<NotificationResponse>>builder()
                            .message("Error retrieving notifications: " + e.getMessage())
                            .build());
        }
    }
}


