package com.global.project.repository;

import com.global.project.entity.Notification;
import com.global.project.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getNotificationByUsername(String username);

    List<Notification> findByUsernameAndStatus(String username, NotificationStatus status);

}
