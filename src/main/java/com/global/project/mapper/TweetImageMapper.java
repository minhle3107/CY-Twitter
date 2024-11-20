package com.global.project.mapper;

import com.global.project.dto.TweetImageResponse;
import com.global.project.entity.TweetImage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TweetImageMapper {
    TweetImageResponse toResponse(TweetImage tweetImage) {
        return TweetImageResponse.builder()
                .tweetId(tweetImage.getTweet().getId())
                .url(tweetImage.getImageUrl())
                .build();
    }

    List<TweetImageResponse> toListResponse(List<TweetImage> tweetImageList) {
        if (tweetImageList.isEmpty()) {
            return Collections.emptyList();
        }
        return tweetImageList.stream().map(this::toResponse).toList();
    }
}
