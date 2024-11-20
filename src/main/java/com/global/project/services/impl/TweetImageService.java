package com.global.project.services.impl;

import com.global.project.entity.Tweet;
import com.global.project.entity.TweetImage;
import com.global.project.repository.TweetImageRepository;
import com.global.project.services.IImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TweetImageService implements IImageService {
    TweetImageRepository tweetImageRepository;

    @Override
    public int insertImage(List<String> urls, Long tweetId) {
        try {
            List<TweetImage> newImages = urls.stream().map(url -> TweetImage.builder()
                    .imageUrl(url)
                    .tweet(Tweet.builder()
                            .id(tweetId)
                            .build())
                    .createdAt(LocalDateTime.now())
                    .build()).toList();

            tweetImageRepository.saveAll(newImages);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
