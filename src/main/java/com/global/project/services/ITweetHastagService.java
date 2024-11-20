package com.global.project.services;

import java.util.List;

public interface ITweetHastagService {
    int insertTweetHastag(List<String> hastagName, Long tweetId);
}
