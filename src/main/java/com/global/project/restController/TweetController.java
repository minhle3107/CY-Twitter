package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.services.impl.TweetService;
import com.global.project.utils.Const;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/tweets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TweetController {
    TweetService tweetService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createTweet(
            @RequestPart("file") MultipartFile file) throws IOException {

        if (file != null) {
            if (file.getSize() > 10 * 1024 * 1024) {
                return ApiResponse.builder()
                        .code(2000)
                        .message("Payload to large")
                        .build();
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ApiResponse.builder()
                        .code(2000)
                        .message("Payload to large")
                        .build();
            }
        }
        return ApiResponse.builder()
                .data(tweetService.storeFile(file))
                .build();
    }


}
