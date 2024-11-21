package com.global.project.services;

import com.global.project.dto.ChatRoomResponse;
import com.global.project.modal.ChatRoomRequest;

public interface IChatRoomService {

    ChatRoomResponse save(ChatRoomRequest chatRoomRequest);


}
