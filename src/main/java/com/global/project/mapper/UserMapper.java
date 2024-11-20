package com.global.project.mapper;

import com.global.project.dto.AccountResponse;
import com.global.project.dto.UserResponse;
import com.global.project.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(Account account) {
        return UserResponse.builder()
                .username(account.getUser().getUsername())
                .name(account.getUser().getName())
                .avatar(account.getUser().getAvatar())
                .coverPhoto(account.getUser().getCoverPhoto())
                .gender(account.getUser().getGender())
                .location(account.getUser().getLocation())
                .website(account.getUser().getWebsite())
                .dob(account.getUser().getDob())
                .bio(account.getUser().getBio())
                .email(account.getEmail())
                .role(account.getRole().getName())
                .build();
    }
}
