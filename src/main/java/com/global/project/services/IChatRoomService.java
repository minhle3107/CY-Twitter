package com.global.project.services;

import com.global.project.dto.ChatRoomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IChatRoomService {
//    ResponseEntity<ChatRoomResponse> createChatRoom(ChatRoomRequest chatRoomRequest);

    ResponseEntity<List<ChatRoomResponse>> getChatRooms();

    ResponseEntity<ChatRoomResponse> getChatRoomDetails(String receiveUsername);
}