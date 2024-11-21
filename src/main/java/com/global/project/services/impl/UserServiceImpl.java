package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.UserResponse;
import com.global.project.entity.User;
import com.global.project.mapper.UserMapper;
import com.global.project.repository.UserRepository;
import com.global.project.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository _userRepository;
    private UserMapper _userMapper;
    private JwtProvider _jwtProvider;
    public UserServiceImpl(UserRepository userRepository,UserMapper userMapper,JwtProvider jwtProvider){
        _userRepository = userRepository;
        _userMapper = userMapper;
        _jwtProvider = jwtProvider;
    }
    @Override
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAllExceptCurrentUser() {
        try {
            String username = _jwtProvider.getUsernameContext();
            List<User> users = _userRepository.findAllExceptCurrentUser(username);
            List<UserResponse> userResponses = users.stream()
                    .map(user -> _userMapper.toResponse(user))
                    .toList();
            ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                    .message("Users retrieved successfully.")
                    .data(userResponses)
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<UserResponse>>builder()
                            .message("An error occurred while retrieving users.")
                            .build());
        }
    }
}
