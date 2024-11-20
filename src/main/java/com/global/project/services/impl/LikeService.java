package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.LikeResponse;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.modal.LikeRequest;
import com.global.project.repository.ILikeRepository;
import com.global.project.services.ILikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeService implements ILikeService {

    private final ILikeRepository likeRepository;

    private final JwtProvider jwtProvider;


    public LikeService(ILikeRepository likeRepository, JwtProvider jwtProvider) {
        this.likeRepository = likeRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity<ApiResponse<LikeResponse>> likeOrUnlike(LikeRequest likeRequest, HttpServletRequest request) {
        String username = jwtProvider.getUsernameContext();

        if (username == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }


        return null;


    }
}