package com.global.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TweetMentionResponse {
    Long tweetId;
    String username_mentioned;
}
