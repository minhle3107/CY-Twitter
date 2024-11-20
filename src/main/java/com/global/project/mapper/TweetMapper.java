package com.global.project.mapper;

import com.global.project.dto.TweetResponse;
import com.global.project.entity.Tweet;
import com.global.project.modal.TweetRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TweetMapper {
    public Tweet toEntity(TweetRequest tweetRequest) {
        return Tweet.builder()
                .type(tweetRequest.getType())
                .audience(tweetRequest.getAudience())
                .content(tweetRequest.getContent())
                .build();
    }

    public TweetResponse toResponse(Tweet tweet) {
        return TweetResponse.builder()
                .id(tweet.getId())
                .type(tweet.getType())
                .audience(tweet.getAudience())
                .content(tweet.getContent())
                .user(tweet.getUser())
                .updatedAt(tweet.getUpdatedAt())
                .createdAt(tweet.getCreatedAt())
                .parentId(tweet.getParentId())
                .userViews(tweet.getUserViews())
                .build();
    }


    public List<TweetResponse> toListResponse(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return Collections.emptyList();
        }
        return tweets.stream().map(this::toResponse).toList();
    }
}
