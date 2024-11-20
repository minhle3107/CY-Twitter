package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.LikeResponse;
import com.global.project.entity.Like;
import com.global.project.entity.Tweet;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.LikeMapper;
import com.global.project.modal.LikeRequest;
import com.global.project.repository.ILikeRepository;
import com.global.project.repository.TweetRepository;
import com.global.project.services.ILikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService implements ILikeService {

    private final ILikeRepository likeRepository;

    private final JwtProvider jwtProvider;

    private final TweetRepository tweetRepository;


    public LikeService(ILikeRepository likeRepository, JwtProvider jwtProvider, TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.jwtProvider = jwtProvider;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<LikeResponse>> likeOrUnlike(LikeRequest likeRequest) {
        String username = jwtProvider.getUsernameContext();

        if (username == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        Long tweetId = likeRequest.getTweetId();

        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if (tweetOptional.isEmpty()) {
            throw new AppException(ErrorCode.TWEET_NOT_FOUND);
        }


        Like likeExists = likeRepository.findLikeByUsernameLikeAndTweetId(username, tweetId);
        LocalDateTime now = LocalDateTime.now();

        if (likeExists == null) {
            Like like = Like.builder()
                    .username(username)
                    .tweetId(likeRequest.getTweetId())
                    .createdAt(now)
                    .build();

            Like likeCreated = likeRepository.save(like);

            LikeResponse likeResponse = LikeMapper.toDto(likeCreated);


            ApiResponse<LikeResponse> apiResponse = ApiResponse.<LikeResponse>builder()
                    .message("Like tweet successfully")
                    .data(likeResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        }

        likeRepository.delete(likeExists);
        ApiResponse<LikeResponse> apiResponse = ApiResponse.<LikeResponse>builder()
                .message("Un Like tweet successfully")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


    }
}
