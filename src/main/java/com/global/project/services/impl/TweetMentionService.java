package com.global.project.services.impl;

import com.global.project.entity.Tweet;
import com.global.project.entity.TweetMention;
import com.global.project.repository.TweetMentionRepository;
import com.global.project.repository.TweetRepository;
import com.global.project.services.ITweetMentionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TweetMentionService implements ITweetMentionService {
    TweetMentionRepository tweetMentionRepository;
    TweetRepository tweetRepository;

    @Override
    public int createTweetMention(List<String> username, Long tweetId) {
        Tweet tweetEntity = tweetRepository.findById(tweetId).get();
        try {
            List<TweetMention> newTweetMention = username.stream().map(usernameMention -> TweetMention.builder()
                    .tweet(tweetEntity)
                    .mentionedUsername(usernameMention)
                    .createdAt(LocalDateTime.now())
                    .build()).toList();

            tweetMentionRepository.saveAll(newTweetMention);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }
}
