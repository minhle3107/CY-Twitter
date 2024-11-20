package com.global.project.mapper;

import com.global.project.dto.BookMarkResponse;
import com.global.project.entity.Bookmark;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {
    public static BookMarkResponse toDto(Bookmark bookmark) {
        return BookMarkResponse.builder()
                .id(bookmark.getId())
                .tweetId(bookmark.getTweetId())
                .username(bookmark.getUsername())
                .createdAt(bookmark.getCreatedAt())
                .build();
    }
}
