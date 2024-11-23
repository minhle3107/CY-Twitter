package com.global.project.mapper;

import com.global.project.dto.ChatMessageResponse;
import com.global.project.entity.ChatMessage;
import com.global.project.modal.ChatMessageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessageMapper {
    public static ChatMessageResponse toDto(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .senderUsername(chatMessage.getSenderUsername())
                .receiveUsername(chatMessage.getReceiveUsername())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public static List<ChatMessageResponse> toDtoList(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(ChatMessageMapper::toDto).collect(Collectors.toList());
    }

    public static ChatMessage toEntity(ChatMessageRequest chatMessage) {
        return ChatMessage.builder()
                .chatRoomId(chatMessage.getChatRoomId())
                .senderUsername(chatMessage.getSenderUsername())
                .receiveUsername(chatMessage.getReceiveUsername())
                .content(chatMessage.getContent())
                .build();
    }
}