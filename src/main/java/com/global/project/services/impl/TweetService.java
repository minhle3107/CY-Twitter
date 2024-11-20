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
import java.util.ArrayList;
import java.util.List;
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
    TweetMentionService mentionService;
    TweetImageService tweetImageService;
    private final TweetHastagService tweetHastagService;

    @Override
    public TweetResponse createTweet(TweetRequest tweetRequest) throws IOException {
        Optional<User> optional = userRepository.findByUsername(tweetRequest.getUsername());
        if (optional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Tweet tweet = Tweet.builder()
                .user(optional.get())
                .type(tweetRequest.getType())
                .content(tweetRequest.getContent())
                .audience(tweetRequest.getAudience())
                .parentId(tweetRequest.getParentId())
                .userViews(0)
                .hastags(new ArrayList<>())
                .images(new ArrayList<>())
                .mentions(new ArrayList<>())
                .build();

        TweetResponse tweetResponse = tweetMapper.toResponse(tweetRepository.saveAndFlush(tweet));

        if (tweetRequest.getHastags() != null) {
            int resultInsertHastag = hastagService.insertHasTags(tweetRequest.getHastags());
            if (resultInsertHastag != 1) {
                tweetRepository.deleteById(tweetResponse.getId());
                throw new AppException(ErrorCode.HASTAG_CREATE_LIST_FAILED);
            }
        }

        if (tweetRequest.getUsernames() != null) {
            int resultInsertMention = mentionService.createTweetMention(tweetRequest.getUsernames(), tweetResponse.getId());
            if (resultInsertMention != 1) {
                tweetRepository.deleteById(tweetResponse.getId());
                throw new AppException(ErrorCode.TWEET_MENTOIN_CREATE_LIST_FAILED);
            }
        }

        if (tweetRequest.getHastags() != null) {
            int resultInsertTweetHastag = tweetHastagService.insertTweetHastag(tweetRequest.getHastags(), tweetResponse.getId());
            if (resultInsertTweetHastag != 1) {
                tweetRepository.deleteById(tweetResponse.getId());
                throw new AppException(ErrorCode.TWEET_HASTAG_CREATE_LIST_FAILED);
            }
        }

        if (tweetRequest.getTweetImages() != null) {
            List<String> images = storeFile(tweetRequest.getTweetImages());
            int resultInsertImage = tweetImageService.insertImage(images, tweetResponse.getId());
            if (resultInsertImage != 1) {
                tweetRepository.deleteById(tweetResponse.getId());
                throw new AppException(ErrorCode.IMAGE_CREATE_LIST_FAILD);
            }
        }

        return tweetResponse;
    }

    public List<String> storeFile(List<MultipartFile> listFile) throws IOException {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : listFile) {
            if (file != null) {
                if (file.getSize() > 10 * 1024 * 1024) {
                    throw new AppException(ErrorCode.IMAGE_SIZE_TOO_LARGE);
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new AppException(ErrorCode.IMAGE_NOT_EXACT_TYPE);
                }
            }
            Path uploadDir = Paths.get("src/main/resources/static/images");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destination = uploadDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String url = "images/" + uniqueFilename;
            images.add(url);
        }
        return images;
    }

}
