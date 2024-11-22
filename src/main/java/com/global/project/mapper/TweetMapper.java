package com.global.project.mapper;

import com.global.project.dto.TweetResponse;
import com.global.project.entity.Tweet;
import com.global.project.modal.TweetRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TweetMapper {
    TweetImageMapper tweetImageMapper;
    TweetMentionMapper tweetMentionMapper;
    TweetHastagMapper tweetHastagMapper;

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
                .images(tweetImageMapper.toListResponse(tweet.getImages()))
                .hastags(tweetHastagMapper.toListResponse(tweet.getHastags()))
                .mentions(tweetMentionMapper.toListResponse(tweet.getMentions()))
                .build();
    }


    public List<TweetResponse> toListResponse(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return Collections.emptyList();
        }
        return tweets.stream().map(this::toResponse).toList();
    }

}
