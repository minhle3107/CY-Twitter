package com.global.project.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetRequest {
    String username;
    int type;
    int audience;
    String content;
    Long parentId;
    List<String> hastags;
    List<MultipartFile> tweetImages;
    List<String> usernames;
}
