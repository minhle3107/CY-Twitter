package com.global.project.services;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.LikeResponse;
import com.global.project.modal.LikeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ILikeService {
    ResponseEntity<ApiResponse<LikeResponse>> likeOrUnlike(LikeRequest likeRequest, HttpServletRequest request);
}
