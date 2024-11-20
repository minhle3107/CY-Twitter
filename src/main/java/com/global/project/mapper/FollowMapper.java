package com.global.project.mapper;

import com.global.project.entity.Follower;
import com.global.project.modal.FollowRequest;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    public Follower toEntity(FollowRequest followRequest){
        return Follower.builder()
                .followedUsername(followRequest.getFollowed_userName())
                .build();
    }
}
