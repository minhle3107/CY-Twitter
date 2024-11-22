package com.global.project.repository;

import com.global.project.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);

    List<ChatMessage> findByReceiveUsernameAndDeliveredFalse(String receiveUsername);

}