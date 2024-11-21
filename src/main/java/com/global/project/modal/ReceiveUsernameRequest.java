package com.global.project.modal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveUsernameRequest {
    @Schema(title = "receiveUsername", example = "chang")
    @NotBlank
    private String receiveUsername;
}
