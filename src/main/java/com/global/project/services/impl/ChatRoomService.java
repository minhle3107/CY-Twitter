package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ChatRoomResponse;
import com.global.project.entity.ChatRoom;
import com.global.project.entity.User;
import com.global.project.repository.ChatRoomRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.IChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService implements IChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository, JwtProvider jwtProvider) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

//    @Override
//    public ResponseEntity<ChatRoomResponse> createChatRoom(ChatRoomRequest chatRoomRequest) {
//        String receiveUsername = chatRoomRequest.getReceiveUsername();
//        String senderUsername = jwtProvider.getUsernameContext();
//
//        Optional<User> receiveUserOpt = userRepository.findById(receiveUsername);
//        if (receiveUserOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        ChatRoom chatRoom = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername)
//                .orElseGet(() -> chatRoomRepository.save(ChatRoom.builder()
//                        .senderUsername(senderUsername)
//                        .receiveUsername(receiveUsername)
//                        .build()));
//
//        User receiveUser = receiveUserOpt.get();
//
//        ChatRoomResponse response = ChatRoomResponse.builder()
//                .id(chatRoom.getId())
//                .senderUsername(chatRoom.getSenderUsername())
//                .receiveUsername(chatRoom.getReceiveUsername())
//                .receiveName(receiveUser.getName())
//                .receiveUserAvatar(receiveUser.getAvatar())
//                .createdAt(chatRoom.getCreatedAt())
//                .build();
//
//        return ResponseEntity.ok(response);
//    }

    @Override
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms() {
        String senderUsername = jwtProvider.getUsernameContext();

        List<ChatRoom> chatRooms = chatRoomRepository.findBySenderUsername(senderUsername);
        List<ChatRoomResponse> responses = chatRooms.stream().map(chatRoom -> {
            Optional<User> receiveUserOpt = userRepository.findById(chatRoom.getReceiveUsername());
            User receiveUser = receiveUserOpt.orElse(new User());
            return ChatRoomResponse.builder()
                    .id(chatRoom.getId())
                    .senderUsername(chatRoom.getSenderUsername())
                    .receiveUsername(chatRoom.getReceiveUsername())
                    .receiveName(receiveUser.getName())
                    .receiveUserAvatar(receiveUser.getAvatar())
                    .createdAt(chatRoom.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @Override
    public ResponseEntity<ChatRoomResponse> getChatRoomDetails(String receiveUsername) {
        String senderUsername = jwtProvider.getUsernameContext();
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername);
        if (chatRoomOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ChatRoom chatRoom = chatRoomOpt.get();
        Optional<User> receiveUserOpt = userRepository.findById(chatRoom.getReceiveUsername());
        User receiveUser = receiveUserOpt.orElse(new User());

        ChatRoomResponse response = ChatRoomResponse.builder()
                .id(chatRoom.getId())
                .senderUsername(chatRoom.getSenderUsername())
                .receiveUsername(chatRoom.getReceiveUsername())
                .receiveName(receiveUser.getName())
                .receiveUserAvatar(receiveUser.getAvatar())
                .createdAt(chatRoom.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }
}