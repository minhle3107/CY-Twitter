package com.global.project.repository;

import com.global.project.entity.ChatMessage;
import com.global.project.modal.ChatMessageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessageRequest> findByChatRoomId(String chatId);
}
