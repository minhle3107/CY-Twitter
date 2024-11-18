package com.global.project.services.impl;

import com.global.project.dto.ApiResponse;
import com.global.project.modal.RefreshAccessTokenRequest;
import com.global.project.services.IRefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService implements IRefreshTokenService {
    @Override
    public ResponseEntity<ApiResponse<?>> refreshToken(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> create() {
        return null;
    }
}
