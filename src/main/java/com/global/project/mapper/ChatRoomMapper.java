package com.global.project.mapper;

import com.global.project.dto.ChatRoomResponse;
import com.global.project.entity.ChatRoom;

public class ChatRoomMapper {

    public static ChatRoomResponse toDto(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .senderUsername(chatRoom.getSenderUsername())
                .receiveUsername(chatRoom.getReceiveUsername())
                .createdAt(chatRoom.getCreatedAt())
                .updatedAt(chatRoom.getUpdatedAt())
                .build();
    }
}
