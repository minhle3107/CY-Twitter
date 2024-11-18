package com.global.project.services.impl;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.ApiResponse;
import com.global.project.dto.SignInResponse;
import com.global.project.entity.Account;
import com.global.project.entity.RefreshToken;
import com.global.project.enums.EnumAccountVerifyStatus;
import com.global.project.enums.EnumTokenType;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.modal.RefreshAccessTokenRequest;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.IRefreshTokenRepository;
import com.global.project.services.IRefreshTokenService;
import com.global.project.utils.DateConversion;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RefreshTokenService implements IRefreshTokenService {

    @Value("${jwt.SECRET_REFRESH_TOKEN_KEY}")
    private String jwtSecretRefreshToken;

    private final IRefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository, AccountRepository accountRepository, JwtProvider jwtProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accountRepository = accountRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity<ApiResponse<SignInResponse>> refreshToken(RefreshAccessTokenRequest request) {
        RefreshToken oldToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIAL));

        // ngày hết hạn token nhỏ hơn ngày hiện tại
        if (oldToken.getExp().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = jwtProvider.getKeyByValueFromJWT(jwtSecretRefreshToken, "username", request.getRefreshToken());
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIAL));

        LocalDateTime now = LocalDateTime.now();

        String newRefreshTokenStr = generateNewRefreshToken(oldToken.getExp(), now, username);

        oldToken.setToken(newRefreshTokenStr);
        oldToken.setCreatedAt(now);
        oldToken.setIat(now);
        oldToken.setExp(oldToken.getExp());

        refreshTokenRepository.save(oldToken);

        SignInResponse response = SignInResponse.builder()
                .id(account.getId())
                .type("Bearer")
                .accountToken(jwtProvider.generateTokenByUsername(account.getUsername()))
                .refreshToken(newRefreshTokenStr)
                .username(account.getUsername())
                .email(account.getEmail())
                .isActive(account.getIsActive())
                .roleName(account.getRole().getName())
                .build();

        ApiResponse<SignInResponse> apiResponse = ApiResponse.<SignInResponse>builder()
                .code(0)
                .message("Token refreshed successfully")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    private String generateNewRefreshToken(LocalDateTime exp, LocalDateTime iat, String username) {
        Date expDate = DateConversion.convertLocalDateTimeToDate(exp);
        Date iatDate = DateConversion.convertLocalDateTimeToDate(iat);
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIAL));

        return Jwts.builder()
                .setSubject(Long.toString(account.getId()))
                .claim("username", account.getUsername())
                .claim("token_type", EnumTokenType.RefreshToken.getValue())
                .claim("account_status", EnumAccountVerifyStatus.Verified.getValue())
                .setExpiration(expDate)
                .setIssuedAt(iatDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretRefreshToken)
                .compact();
    }
}