package com.global.project.mapper;

import com.global.project.dto.TweetMentionResponse;
import com.global.project.entity.TweetMention;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TweetMentionMapper {
    TweetMentionResponse toResponse(TweetMention tweetMentions) {
        return TweetMentionResponse.builder()
                .tweetId(tweetMentions.getTweet().getId())
                .username_mentioned(tweetMentions.getMentionedUsername())
                .build();
    }

    List<TweetMentionResponse> toListResponse(List<TweetMention> tweetMentions) {
        if (tweetMentions.isEmpty()) {
            return Collections.emptyList();
        }
        return tweetMentions.stream().map(this::toResponse).toList();
    }
}
