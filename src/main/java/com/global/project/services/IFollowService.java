package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.FollowRequest;
import com.global.project.modal.LogoutRequest;
import org.springframework.http.ResponseEntity;

public interface IFollowService {
    ResponseEntity<ApiResponse<?>> follow(FollowRequest followRequest);
    ResponseEntity<ApiResponse<?>> followFe1(FollowRequest followRequest);
}
