package com.global.project.services;

import com.global.project.dto.TweetResponse;
import com.global.project.modal.TweetRequest;

import java.io.IOException;

public interface ITweetService {
    TweetResponse createTweet(TweetRequest tweetRequest) throws IOException;
}
