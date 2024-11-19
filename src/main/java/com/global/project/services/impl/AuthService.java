package com.global.project.services.impl;

import com.global.project.configuration.AccountDetailsImpl;
import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.SignInResponse;
import com.global.project.entity.RefreshToken;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.modal.SignInRequest;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.IRefreshTokenRepository;
import com.global.project.services.IAuthService;
import com.global.project.utils.DeviceInfoUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtUtils;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRefreshTokenRepository iRefreshTokenRepository;

    @Value("${jwt.JWT_EXPIRATION_REFRESH_TOKEN}")
    private int jwtExpirationRefreshToken;

    public AuthService(AuthenticationManager authenticationManager, JwtProvider jwtUtils, AccountRepository accountRepository, PasswordEncoder passwordEncoder, IRefreshTokenRepository iRefreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.iRefreshTokenRepository = iRefreshTokenRepository;
    }

    @Override
    public ResponseEntity<ApiResponse<SignInResponse>> login(SignInRequest signinRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticateUser(signinRequest);
            AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();
            Long accountId = accountDetails.getAccount().getId();

            LocalDateTime now = LocalDateTime.now();

            String deviceInfo = DeviceInfoUtil.getDeviceInfo(request);

            String accountToken = jwtUtils.generateTokenByUsername(accountDetails.getUsername());

            String refreshToken = jwtUtils.generateRefreshTokenByUsername(now, accountDetails.getUsername());

            saveOrUpdateRefreshToken(accountId, refreshToken, now, deviceInfo);

            SignInResponse signinResponse = SignInResponse.builder()
                    .id(accountId)
                    .type("Bearer")
                    .accountToken(accountToken)
                    .refreshToken(refreshToken)
                    .username(accountDetails.getUsername())
                    .email(accountDetails.getAccount().getEmail())
                    .isActive(accountDetails.getAccount().getIsActive())
                    .roleName(accountDetails.getRoleName())
                    .build();

            ApiResponse<SignInResponse> apiResponse = ApiResponse.<SignInResponse>builder()
                    .data(signinResponse)
                    .message("Login success")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            throw new AppException(ErrorCode.LOGIN_FAILED);
        }
    }

    private void saveOrUpdateRefreshToken(Long accountId, String refreshToken, LocalDateTime now, String deviceInfo) {
        RefreshToken existingToken = iRefreshTokenRepository.findByAccountIdAndDeviceInfo(accountId, deviceInfo);
        if (existingToken != null) {
            existingToken.setToken(refreshToken);
            existingToken.setCreatedAt(now);
            existingToken.setExp(now.plus(Duration.ofMillis(jwtExpirationRefreshToken)));
            existingToken.setIat(now);
            iRefreshTokenRepository.save(existingToken);
        } else {
            RefreshToken refreshTokenModel = RefreshToken.builder()
                    .accountId(accountId)
                    .token(refreshToken)
                    .createdAt(now)
                    .exp(now.plus(Duration.ofMillis(jwtExpirationRefreshToken)))
                    .iat(now)
                    .deviceInfo(deviceInfo)
                    .build();
            iRefreshTokenRepository.save(refreshTokenModel);
        }
    }

    private Authentication authenticateUser(SignInRequest signinRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
    }


}