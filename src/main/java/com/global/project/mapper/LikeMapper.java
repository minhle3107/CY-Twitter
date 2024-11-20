package com.global.project.mapper;

import com.global.project.dto.LikeResponse;
import com.global.project.entity.Like;
import com.global.project.modal.LikeRequest;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public static LikeResponse toDto(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .tweetId(like.getTweetId())
                .username(like.getUsername())
                .createdAt(like.getCreatedAt())
                .build();
    }

    public static Like toEntity(LikeRequest likeRequest) {
        return Like.builder()
                .tweetId(likeRequest.getTweetId())
                .build();
    }

}
