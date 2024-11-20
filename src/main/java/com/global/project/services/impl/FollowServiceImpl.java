package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.entity.Follower;
import com.global.project.entity.User;
import com.global.project.modal.FollowRequest;
import com.global.project.repository.IFollowRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.IFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FollowServiceImpl implements IFollowService {

    private IFollowRepository _followRepository;
    private UserRepository _userRepository;
    private JwtProvider _jwtProvider;

    public FollowServiceImpl(IFollowRepository followRepository, JwtProvider jwtProvider, UserRepository userRepository) {
        this._followRepository = followRepository;
        this._jwtProvider = jwtProvider;
        _userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> follow(FollowRequest followRequest) {

        try {
            String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest().getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);
                String username = _jwtProvider.getUsernameFromJwt(token);
                Optional<User> userToFollow = _userRepository.findByUsername(followRequest.getFollowed_userName());
                if (userToFollow.isEmpty()) {
                    return ResponseEntity.badRequest().body(
                            ApiResponse.builder()
                                    .message("Người dùng bạn đang cố gắng theo dõi không tồn tại.")
                                    .build()
                    );
                }
                Follower existingFollower = _followRepository.findByUsernameAndFollowedUsername(username, followRequest.getFollowed_userName());
                if (existingFollower != null) {
                    _followRepository.delete(existingFollower);
                    return ResponseEntity.ok(
                            ApiResponse.builder()
                                    .message("Unfollowed successfully.")
                                    .build()
                    );
                }
                Follower newFollower = Follower.builder()
                        .username(username)
                        .createdAt(LocalDateTime.now())
                        .followedUsername(followRequest.getFollowed_userName())
                        .build();
                _followRepository.save(newFollower);
                return ResponseEntity.ok(
                        ApiResponse.builder()
                                .message("Followed successfully.")
                                .build()
                );
            } else {
                return ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message("Invalid or missing token.")
                                .build()
                );
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ApiResponse.builder()
                            .message("Đã xảy ra lỗi khi theo dõi/bỏ theo dõi người dùng.")
                            .build()
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> followFe1(FollowRequest followRequest) {
        try {
            String username = _jwtProvider.getUsernameContext();
            Optional<User> userToFollow = _userRepository.findByUsername(followRequest.getFollowed_userName());
            if (userToFollow.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .message("Người dùng bạn đang cố gắng theo dõi không tồn tại.")
                                .build()
                );
            }
            Follower existingFollower = _followRepository.findByUsernameAndFollowedUsername(username, followRequest.getFollowed_userName());
            if (existingFollower != null) {
                _followRepository.delete(existingFollower);
                return ResponseEntity.ok(
                        ApiResponse.builder()
                                .message("Unfollowed successfully.")
                                .build()
                );
            }
            Follower newFollower = Follower.builder()
                    .username(username)
                    .createdAt(LocalDateTime.now())
                    .followedUsername(followRequest.getFollowed_userName())
                    .build();
            _followRepository.save(newFollower);
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .message("Followed successfully.")
                            .build()
            );

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ApiResponse.builder()
                            .message("Đã xảy ra lỗi khi theo dõi/bỏ theo dõi người dùng.")
                            .build()
            );
        }
    }


}
