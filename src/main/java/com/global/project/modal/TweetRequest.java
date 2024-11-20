package com.global.project.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

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
    int parentId;
    List<String> hastags;
    List<String> tweetImages;
    List<String> usernames;
}
