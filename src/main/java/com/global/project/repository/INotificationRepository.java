package com.global.project.repository;

import com.global.project.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification,Long>{
}
