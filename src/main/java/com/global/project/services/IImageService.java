package com.global.project.services;

import java.util.List;

public interface IImageService {
    int insertImage(List<String> urls, Long tweetId);
}
