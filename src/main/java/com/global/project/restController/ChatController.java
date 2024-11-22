// ChatController.java
package com.global.project.restController;

import com.global.project.mapper.ChatMessageMapper;
import com.global.project.modal.ChatMessageRequest;
import com.global.project.repository.ChatMessageRepository;
import com.global.project.utils.WebSocketEventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageRepository chatMessageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageRepository = chatMessageRepository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        if (WebSocketEventListener.isUserConnected(chatMessageRequest.getReceiveUsername())) {
            messagingTemplate.convertAndSendToUser(chatMessageRequest.getReceiveUsername(), "/queue/messages", chatMessageRequest);
        } else {
            chatMessageRequest.setDelivered(false);
            chatMessageRepository.save(ChatMessageMapper.toEntity(chatMessageRequest));
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessageRequest chatMessageRequest) {
        if (WebSocketEventListener.isUserConnected(chatMessageRequest.getReceiveUsername())) {
            messagingTemplate.convertAndSendToUser(chatMessageRequest.getReceiveUsername(), "/queue/messages", chatMessageRequest);
        } else {
            chatMessageRequest.setDelivered(false);
            chatMessageRepository.save(ChatMessageMapper.toEntity(chatMessageRequest));
        }
    }
}