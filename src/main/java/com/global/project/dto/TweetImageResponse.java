package com.global.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class TweetImageResponse {
    Long tweetId;
    String url;
}
