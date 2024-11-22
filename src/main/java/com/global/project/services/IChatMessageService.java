package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatMessageResponse;
import com.global.project.modal.ChatMessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IChatMessageService {
    ResponseEntity<ApiResponse<ChatMessageResponse>> save(ChatMessageRequest chatMessageRequest);


//    ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(String senderId, String recipientId);

    ResponseEntity<ApiResponse<List<ChatMessageResponse>>> findChatMessages(String receiveUsername);
}
