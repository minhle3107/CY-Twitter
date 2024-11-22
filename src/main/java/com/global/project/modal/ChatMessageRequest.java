package com.global.project.modal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    @Schema(title = "receiveUsername", example = "chang")
    @NotBlank
    private String receiveUsername;

    private Long chatRoomId;

    private String senderUsername;

    private boolean delivered;

    @Schema(title = "content", example = "Hello World")
    @NotBlank
    private String content;

}
