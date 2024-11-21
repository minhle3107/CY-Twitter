package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<ApiResponse<List<UserResponse>>> findAllExceptCurrentUser();
}
