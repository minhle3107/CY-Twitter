package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.dto.SignInResponse;
import com.global.project.modal.RefreshAccessTokenRequest;
import com.global.project.services.IRefreshTokenService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "02. ACCOUNT")
@RequestMapping(value = Const.PREFIX_VERSION + "/accounts")
public class RestAccountController {

    public RestAccountController(IRefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    private final IRefreshTokenService refreshTokenService;

    @Operation(summary = "Refresh Token", description = "Refresh Token", tags = {"02. ACCOUNT"})
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<SignInResponse>> refreshToken(@RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest) {
        return refreshTokenService.refreshToken(refreshAccessTokenRequest);
    }
}