package com.global.project.services;

import com.global.project.dto.AccountResponse;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.UserResponse;
import com.global.project.modal.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    ApiResponse<AccountResponse> registerAccount(SignupRequest signupRequest);

    ResponseEntity<ApiResponse<AccountResponse>> getMe();

    ResponseEntity<ApiResponse<UserResponse>> getMe1();
}
