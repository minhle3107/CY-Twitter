package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.TweetRequest;
import com.global.project.services.impl.TweetService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/tweets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "03. Tweet")
@MultipartConfig
public class TweetController {
    TweetService tweetService;

    @PostMapping(value = "")
    public ApiResponse<?> createTweet( TweetRequest tweetRequest) throws IOException {
        return ApiResponse.builder()
                .data(tweetService.createTweet(tweetRequest))
                .message("Created tweet successfully")
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<?> getTweet(@PathVariable("id") Long id) throws IOException {
        return tweetService.getById(id);
    }

    @GetMapping("")
    public ApiResponse<?> getTweetPagination(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return tweetService.getAllTweetsPagination(pageSize, pageNumber);
    }


}
