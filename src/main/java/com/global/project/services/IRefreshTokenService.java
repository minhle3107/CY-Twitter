package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.SignInResponse;
import com.global.project.modal.RefreshAccessTokenRequest;
import org.springframework.http.ResponseEntity;

public interface IRefreshTokenService {

    ResponseEntity<ApiResponse<SignInResponse>> refreshToken(RefreshAccessTokenRequest refreshAccessTokenRequest);

}