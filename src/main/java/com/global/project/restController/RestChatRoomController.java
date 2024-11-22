package com.global.project.restController;

import com.global.project.dto.ChatRoomResponse;
import com.global.project.services.IChatRoomService;
import com.global.project.utils.Const;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/chat/rooms")
public class RestChatRoomController {

    private final IChatRoomService chatRoomService;

    public RestChatRoomController(IChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

//    @PostMapping
//    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
//        return chatRoomService.createChatRoom(chatRoomRequest);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms() {
        return chatRoomService.getChatRooms();
    }


    @GetMapping()
    public ResponseEntity<ChatRoomResponse> getChatRoomDetails(@RequestParam String receiveUsername) {
        return chatRoomService.getChatRoomDetails(receiveUsername);
    }
}