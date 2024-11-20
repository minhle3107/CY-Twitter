package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.TweetRequest;
import com.global.project.services.impl.TweetService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/tweets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "03. Tweet")
public class TweetController {
    TweetService tweetService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createTweet(@RequestParam("username") String username,
                                      @RequestParam(value = "type", defaultValue = "1") int type,
                                      @RequestParam(value = "audience", defaultValue = "1") int audience,
                                      @RequestParam("content") String content,
                                      @RequestParam(value = "parentId", defaultValue = "0") Long parentId,
                                      @RequestParam(value = "hashtags", required = false) List<String> hashtags,
                                      @RequestParam(value = "tweetImages", required = false) List<MultipartFile> tweetImages,
                                      @RequestParam(value = "usernames", required = false) List<String> usernames) throws IOException {

        TweetRequest tweetRequest = TweetRequest.builder()
                .username(username)
                .audience(audience)
                .content(content)
                .parentId(parentId)
                .hastags(hashtags)
                .tweetImages(tweetImages)
                .usernames(usernames)
                .audience(audience)
                .type(type)
                .build();

        return ApiResponse.builder()
                .data(tweetService.createTweet(tweetRequest))
                .message("Created tweet successfully")
                .build();
    }


}
