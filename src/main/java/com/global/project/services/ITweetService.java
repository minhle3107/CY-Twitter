package com.global.project.services;

import com.global.project.dto.TweetResponse;
import com.global.project.modal.TweetRequest;

public interface ITweetService {
    TweetResponse createTweet(TweetRequest tweetRequest);
}
