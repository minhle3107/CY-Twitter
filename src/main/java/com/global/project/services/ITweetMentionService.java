package com.global.project.services;

import java.util.List;

public interface ITweetMentionService {
    int createTweetMention(List<String> username,Long tweetId);
}
