package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatMessageResponse;
import com.global.project.dto.ChatNotificationResponse;
import com.global.project.modal.ChatMessageRequest;
import com.global.project.services.impl.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class RestChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public RestChatMessageController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest chatMessageRequest) {
        ResponseEntity<ApiResponse<ChatMessageResponse>> savedMsg = chatMessageService.save(chatMessageRequest);

        ChatMessageResponse chatMessageResponse = savedMsg.getBody().getData();
        messagingTemplate.convertAndSendToUser(
                chatMessageRequest.getReceiveUsername(), "/queue/messages",
                new ChatNotificationResponse(
                        chatMessageResponse.getId(),
                        chatMessageResponse.getSenderUsername(),
                        chatMessageResponse.getReceiveUsername(),
                        chatMessageResponse.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(@PathVariable String senderId,
                                                                                   @PathVariable String recipientId) {
        return chatMessageService.findChatMessages(senderId, recipientId);
    }

}