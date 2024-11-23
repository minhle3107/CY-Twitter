package com.global.project.utils;

import com.global.project.configuration.jwtConfig.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final ConcurrentHashMap<String, Boolean> activeUsers = new ConcurrentHashMap<>();

    @Autowired
    private JwtProvider jwtProvider;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String username = jwtProvider.getUsernameContext();
        if (username != null) {
            activeUsers.put(username, true);
            System.out.println("User Connected: " + username);
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