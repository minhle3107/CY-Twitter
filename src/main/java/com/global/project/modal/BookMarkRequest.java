package com.global.project.modal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMarkRequest {
    @Schema(title = "Tweet ID", example = "1")
    private Long tweetId;
}
