package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.BookMarkResponse;
import com.global.project.entity.Bookmark;
import com.global.project.entity.Tweet;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.BookmarkMapper;
import com.global.project.modal.BookMarkRequest;
import com.global.project.repository.IBookMarkRepository;
import com.global.project.repository.TweetRepository;
import com.global.project.services.IBookMarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookMarkService implements IBookMarkService {

    private final IBookMarkRepository iBookMarkRepository;
    private final JwtProvider jwtProvider;

    private final TweetRepository tweetRepository;

    public BookMarkService(IBookMarkRepository iBookMarkRepository, JwtProvider jwtProvider, TweetRepository tweetRepository) {
        this.iBookMarkRepository = iBookMarkRepository;
        this.jwtProvider = jwtProvider;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<BookMarkResponse>> bookMarkOrUnBookMark(BookMarkRequest bookMarkRequest) {
        String username = jwtProvider.getUsernameContext();

        if (username == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        Long tweetId = bookMarkRequest.getTweetId();

        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if (tweetOptional.isEmpty()) {
            throw new AppException(ErrorCode.TWEET_NOT_FOUND);
        }


        Bookmark bookmarkExists = iBookMarkRepository.findLikeByUsernameAndTweetId(username, tweetId);
        LocalDateTime now = LocalDateTime.now();

        if (bookmarkExists == null) {
            Bookmark bookmark = Bookmark.builder()
                    .username(username)
                    .tweetId(bookMarkRequest.getTweetId())
                    .createdAt(now)
                    .build();

            Bookmark bookmarkCreated = iBookMarkRepository.save(bookmark);

            BookMarkResponse bookMarkResponse = BookmarkMapper.toDto(bookmarkCreated);


            ApiResponse<BookMarkResponse> apiResponse = ApiResponse.<BookMarkResponse>builder()
                    .message("Bookmark tweet successfully")
                    .data(bookMarkResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        }

        iBookMarkRepository.delete(bookmarkExists);
        ApiResponse<BookMarkResponse> apiResponse = ApiResponse.<BookMarkResponse>builder()
                .message("Un Bookmark tweet successfully")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
