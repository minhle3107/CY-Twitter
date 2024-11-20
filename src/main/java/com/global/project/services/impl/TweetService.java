package com.global.project.services.impl;

import com.global.project.dto.TweetResponse;
import com.global.project.entity.Tweet;
import com.global.project.entity.User;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.TweetMapper;
import com.global.project.modal.TweetRequest;
import com.global.project.repository.HastagRepository;
import com.global.project.repository.TweetMentionRepository;
import com.global.project.repository.TweetRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.ITweetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TweetService implements ITweetService {
    HastagRepository hastagRepository;
    TweetRepository tweetRepository;
    TweetMentionRepository mentionRepository;
    TweetMapper tweetMapper;
    UserRepository userRepository;
    HastagService hastagService;

    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest) {
        Optional<User> optional = userRepository.findByUsername(tweetRequest.getUsername());
        if (optional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        int resultInsertHastag = hastagService.insertHasTags(tweetRequest.getHastags());

        Tweet tweet = Tweet.builder()
                .type(tweetRequest.getType())
                .content(tweetRequest.getContent())
                .audience(tweetRequest.getAudience())
                .build();
        return tweetMapper.toResponse(tweetRepository.save(tweet));
    }

    public String storeFile(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("src/main/resources/static/images");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destination = uploadDir.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return "images/" + uniqueFilename;
    }

}
