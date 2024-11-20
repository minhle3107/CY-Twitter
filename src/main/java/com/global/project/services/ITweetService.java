package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.TweetResponse;
import com.global.project.modal.TweetRequest;

import java.io.IOException;
import java.util.List;

public interface ITweetService {
    TweetResponse createTweet(TweetRequest tweetRequest) throws IOException;

    ApiResponse<TweetResponse> getById(Long id);

    List<TweetResponse> getAllTweets();

    ApiResponse<List<TweetResponse>> getAllTweetsPagination(int pageSize, int pageNumber);
}
