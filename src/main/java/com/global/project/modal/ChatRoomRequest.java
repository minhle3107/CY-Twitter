package com.global.project.modal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomRequest {
    
    @Schema(title = "receiveUsername", example = "chang")
    @NotBlank
    private String receiveUsername;
}
