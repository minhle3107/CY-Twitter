package com.global.project.mapper;

import com.global.project.dto.TweetHastagResponse;
import com.global.project.entity.TweetHastag;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TweetHastagMapper {
    TweetHastagResponse toResponse(TweetHastag tweetHastag) {
        return TweetHastagResponse.builder()
                .tweetId(tweetHastag.getTweet().getId())
                .name(tweetHastag.getHastag().getName())
                .build();
    }

    List<TweetHastagResponse> toListResponse(List<TweetHastag> tweetHastags) {
        if(tweetHastags.isEmpty()){
            return Collections.emptyList();
        }
        return tweetHastags.stream().map(this::toResponse).toList();
    }
}
