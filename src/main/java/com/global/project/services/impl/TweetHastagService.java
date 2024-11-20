package com.global.project.services.impl;

import com.global.project.entity.Hastag;
import com.global.project.entity.Tweet;
import com.global.project.entity.TweetHastag;
import com.global.project.repository.TweetHastagRepository;
import com.global.project.services.ITweetHastagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TweetHastagService implements ITweetHastagService {
    TweetHastagRepository tweetHastagRepository;

    @Override
    public int insertTweetHastag(List<String> hastagNames, Long tweetId) {
        try {
            List<TweetHastag> newTweetHastag = hastagNames.stream().map(hastag -> TweetHastag.builder()
                    .hastag(Hastag.builder()
                            .name(hastag)
                            .build())
                    .tweet(Tweet.builder()
                            .id(tweetId)
                            .build())
                    .build()).toList();

            tweetHastagRepository.saveAll(newTweetHastag);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
