package com.global.project.restController;

import com.global.project.modal.ChatMessageRequest;
import com.global.project.services.IChatMessageService;
import com.global.project.utils.WebSocketEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IChatMessageService iChatMessageService;


    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, IChatMessageService iChatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.iChatMessageService = iChatMessageService;
    }


    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessageRequest sendMessage(ChatMessageRequest chatMessageRequest) {

        // Lưu tin nhắn vào database
        iChatMessageService.save(chatMessageRequest);

        // Kiểm tra xem người nhận có online không
        boolean isReceiverOnline = WebSocketEventListener.isUserConnected(chatMessageRequest.getReceiveUsername());

        if (isReceiverOnline) {
            // Gửi tin nhắn đến queue riêng của người nhận
            messagingTemplate.convertAndSendToUser(
                    chatMessageRequest.getReceiveUsername(),
                    "/queue/messages",
                    chatMessageRequest
            );
        }
        return chatMessageRequest;
    }
}