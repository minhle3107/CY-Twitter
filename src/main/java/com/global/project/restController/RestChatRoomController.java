package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.ChatRoomResponse;
import com.global.project.modal.ChatRoomRequest;
import com.global.project.services.IChatRoomService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "06. CHAT ROOM")
@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/chat-rooms")
public class RestChatRoomController {

    private final IChatRoomService iChatRoomService;

    public RestChatRoomController(IChatRoomService iChatRoomService) {
        this.iChatRoomService = iChatRoomService;
    }

    @Operation(summary = "Create Chat Room", description = "Create Chat Room", tags = {"06. CHAT ROOM"})
    @PostMapping("")
    public ResponseEntity<ApiResponse<ChatRoomResponse>> save(@RequestBody ChatRoomRequest chatRoomRequest) {
        return iChatRoomService.save(chatRoomRequest);
    }
}
