package com.global.project.utils;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.entity.ChatMessage;
import com.global.project.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final ConcurrentHashMap<String, Boolean> activeUsers = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String username = jwtProvider.getUsernameContext();
        if (username != null) {
            activeUsers.put(username, true);
            System.out.println("User Connected: " + username);

            // Gửi lại tin nhắn chưa được gửi
            List<ChatMessage> undeliveredMessages = chatMessageRepository.findByReceiveUsernameAndDeliveredFalse(username);
            for (ChatMessage message : undeliveredMessages) {
                messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
                message.setDelivered(true);
                chatMessageRepository.save(message);
            }
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String username = jwtProvider.getUsernameContext();
        if (username != null) {
            activeUsers.remove(username);
            System.out.println("User Disconnected: " + username);
        }
    }

    public static boolean isUserConnected(String username) {
        return activeUsers.containsKey(username);
    }
}