package com.global.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TweetHastagResponse {
    Long tweetId;
    private String name;
}
