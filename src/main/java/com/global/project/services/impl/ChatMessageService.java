package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatMessageResponse;
import com.global.project.dto.ChatRoomResponse;
import com.global.project.entity.ChatMessage;
import com.global.project.mapper.ChatMessageMapper;
import com.global.project.modal.ChatMessageRequest;
import com.global.project.modal.ChatRoomRequest;
import com.global.project.repository.IChatMessageRepository;
import com.global.project.services.IChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService implements IChatMessageService {

    private final IChatMessageRepository iChatMessageRepository;

    private final ChatRoomService chatRoomService;

    private final JwtProvider jwtProvider;


    public ChatMessageService(IChatMessageRepository iChatMessageRepository, ChatRoomService chatRoomService, JwtProvider jwtProvider) {
        this.iChatMessageRepository = iChatMessageRepository;
        this.chatRoomService = chatRoomService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity<ApiResponse<ChatMessageResponse>> save(ChatMessageRequest chatMessageRequest) {

        String receiveUsername = chatMessageRequest.getReceiveUsername();
        String content = chatMessageRequest.getContent();

        ChatRoomRequest chatRoomRequest = ChatRoomRequest.builder()
                .receiveUsername(receiveUsername)
                .build();

        ChatRoomResponse chatRoomResponse = chatRoomService.save(chatRoomRequest);

        Long chatRoomId = chatRoomResponse.getId();

        String senderUsername = jwtProvider.getUsernameContext();

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .senderUsername(senderUsername)
                .receiveUsername(receiveUsername)
                .content(content)
                .build();

        return ResponseEntity.ok(ApiResponse.<ChatMessageResponse>builder()
                .message("Chat Room already exists")
                .data(ChatMessageMapper.toDto(iChatMessageRepository.save(chatMessage)))
                .build());
    }

    @Override
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(String senderId, String recipientId) {

        ChatRoomRequest chatRoomRequest = ChatRoomRequest.builder()
                .receiveUsername(recipientId)
                .build();

        ChatRoomResponse chatRoomResponse = chatRoomService.save(chatRoomRequest);

        Long chatRoomId = chatRoomResponse.getId();


        return null;
    }
}
