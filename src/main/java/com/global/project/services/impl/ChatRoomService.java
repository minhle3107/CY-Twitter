package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ChatRoomResponse;
import com.global.project.entity.ChatRoom;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.ChatRoomMapper;
import com.global.project.modal.ChatRoomRequest;
import com.global.project.repository.IChatRoomRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.IChatRoomService;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService implements IChatRoomService {

    private final JwtProvider jwtProvider;

    private final IChatRoomRepository iChatRoomRepository;

    private final UserRepository userRepository;


    public ChatRoomService(JwtProvider jwtProvider, IChatRoomRepository iChatRoomRepository, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.iChatRoomRepository = iChatRoomRepository;
        this.userRepository = userRepository;
    }


    private boolean usernameExists(String username) {
        return !userRepository.existsByUsername(username);
    }


//    @Override
//    public ResponseEntity<ApiResponse<ChatRoomResponse>> save(ChatRoomRequest chatRoomRequest) {
//        String senderUsername = jwtProvider.getUsernameContext();
//        String receiveUsername = chatRoomRequest.getReceiveUsername();
//
//        if (usernameExists(senderUsername) || usernameExists(receiveUsername)) {
//            throw new AppException(ErrorCode.USER_NOT_FOUND);
//        }
//
//        return checkChatRoom(senderUsername, receiveUsername);
//    }
//
//    private ResponseEntity<ApiResponse<ChatRoomResponse>> checkChatRoom(String senderUsername, String receiveUsername) {
//        ChatRoom chatRoom = iChatRoomRepository.findByUsers(senderUsername, receiveUsername);
//
//        if (chatRoom == null) {
//            return createChatRoom(senderUsername, receiveUsername);
//        }
//
//        return ResponseEntity.ok(ApiResponse.<ChatRoomResponse>builder()
//                .message("Chat Room already exists")
//                .data(ChatRoomMapper.toDto(chatRoom))
//                .build());
//    }
//
//    private ResponseEntity<ApiResponse<ChatRoomResponse>> createChatRoom(String senderUsername, String receiveUsername) {
//        ChatRoom chatRoom = iChatRoomRepository.save(ChatRoom.builder()
//                .senderUsername(senderUsername)
//                .receiveUsername(receiveUsername)
//                .build());
//
//        return ResponseEntity.ok(ApiResponse.<ChatRoomResponse>builder()
//                .message("Chat Room created successfully")
//                .data(ChatRoomMapper.toDto(chatRoom))
//                .build());
//    }

    @Override
    public ChatRoomResponse save(ChatRoomRequest chatRoomRequest) {
        String senderUsername = jwtProvider.getUsernameContext();
        String receiveUsername = chatRoomRequest.getReceiveUsername();

        if (usernameExists(senderUsername) || usernameExists(receiveUsername)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return checkChatRoom(senderUsername, receiveUsername);
    }

    private ChatRoomResponse checkChatRoom(String senderUsername, String receiveUsername) {
        ChatRoom chatRoom = iChatRoomRepository.findByUsers(senderUsername, receiveUsername);

        if (chatRoom == null) {
            return createChatRoom(senderUsername, receiveUsername);
        }

        return ChatRoomMapper.toDto(chatRoom);
    }

    private ChatRoomResponse createChatRoom(String senderUsername, String receiveUsername) {
        ChatRoom chatRoom = iChatRoomRepository.save(ChatRoom.builder()
                .senderUsername(senderUsername)
                .receiveUsername(receiveUsername)
                .build());

        return ChatRoomMapper.toDto(chatRoom);
    }
}
