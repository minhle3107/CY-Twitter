package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatMessageResponse;
import com.global.project.modal.ChatMessageRequest;
import com.global.project.services.IChatMessageService;
import com.global.project.utils.Const;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/chat")
public class RestChatMessageController {

    private final IChatMessageService chatMessageService;

    public RestChatMessageController(IChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<ChatMessageResponse>> sendMessage(@RequestBody ChatMessageRequest chatMessageRequest) {
        return chatMessageService.save(chatMessageRequest);
    }

    @GetMapping("/messages")
    public ResponseEntity<ApiResponse<List<ChatMessageResponse>>> getMessages(@RequestParam String receiveUsername) {
        return chatMessageService.findChatMessages(receiveUsername);
    }
}