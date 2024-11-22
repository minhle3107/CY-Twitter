package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatMessageResponse;
import com.global.project.entity.ChatMessage;
import com.global.project.entity.ChatRoom;
import com.global.project.entity.User;
import com.global.project.mapper.ChatMessageMapper;
import com.global.project.modal.ChatMessageRequest;
import com.global.project.repository.ChatMessageRepository;
import com.global.project.repository.ChatRoomRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.IChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService implements IChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository, UserRepository userRepository, JwtProvider jwtProvider, SimpMessagingTemplate messagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public ResponseEntity<ApiResponse<ChatMessageResponse>> save(ChatMessageRequest chatMessageRequest) {
        String receiveUsername = chatMessageRequest.getReceiveUsername();
        String content = chatMessageRequest.getContent();

        Optional<User> receiveUserOpt = userRepository.findById(receiveUsername);
        if (receiveUserOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.<ChatMessageResponse>builder()
                    .message("User not found")
                    .build());
        }

        String senderUsername = jwtProvider.getUsernameContext();

        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername);
        if (chatRoomOpt.isEmpty()) {
            chatRoomRepository.save(ChatRoom.builder()
                    .senderUsername(senderUsername)
                    .receiveUsername(receiveUsername)
                    .build());

            chatRoomRepository.save(ChatRoom.builder()
                    .senderUsername(receiveUsername)
                    .receiveUsername(senderUsername)
                    .build());
        }

        ChatRoom chatRoom = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername)
                .orElseGet(() -> chatRoomRepository.findBySenderUsernameAndReceiveUsername(receiveUsername, senderUsername).get());

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom.getId())
                .senderUsername(senderUsername)
                .receiveUsername(receiveUsername)
                .content(content)
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSendToUser(receiveUsername, "/queue/notifications", "New message from " + senderUsername);

        return ResponseEntity.ok(ApiResponse.<ChatMessageResponse>builder()
                .message("Message sent")
                .data(ChatMessageMapper.toDto(savedMessage))
                .build());
    }

//    @Override
//    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(String receiveUsername) {
//        String senderUsername = jwtProvider.getUsernameContext();
//
//        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername);
//        if (chatRoomOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body(ApiResponse.<List<ChatMessageResponse>>builder()
//                    .message("Chat room not found")
//                    .build());
//        }
//
//        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomOpt.get().getId());
//        return ResponseEntity.ok(ApiResponse.<List<ChatMessageResponse>>builder()
//                .message("Messages retrieved")
//                .data(ChatMessageMapper.toDtoList(chatMessages))
//                .build());
//    }

    @Override
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(String receiveUsername) {
        String senderUsername = jwtProvider.getUsernameContext();

        Optional<ChatRoom> chatRoomOpt1 = chatRoomRepository.findBySenderUsernameAndReceiveUsername(senderUsername, receiveUsername);
        Optional<ChatRoom> chatRoomOpt2 = chatRoomRepository.findBySenderUsernameAndReceiveUsername(receiveUsername, senderUsername);

        if (chatRoomOpt1.isEmpty() && chatRoomOpt2.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.<List<ChatMessageResponse>>builder()
                    .message("Chat room not found")
                    .build());
        }

        List<ChatMessage> chatMessages = chatRoomOpt1.map(chatRoom -> chatMessageRepository.findByChatRoomId(chatRoom.getId())).orElseGet(List::of);
        chatMessages.addAll(chatRoomOpt2.map(chatRoom -> chatMessageRepository.findByChatRoomId(chatRoom.getId())).orElseGet(List::of));

        chatMessages.sort(Comparator.comparing(ChatMessage::getId));

        return ResponseEntity.ok(ApiResponse.<List<ChatMessageResponse>>builder()
                .message("Messages retrieved")
                .data(ChatMessageMapper.toDtoList(chatMessages))
                .build());
    }
}