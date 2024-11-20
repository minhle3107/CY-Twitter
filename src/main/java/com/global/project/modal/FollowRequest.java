package com.global.project.modal;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequest {
    private String followed_userName;
}
