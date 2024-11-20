package com.global.project.services;

import com.global.project.dto.AccountResponse;
import com.global.project.dto.ApiResponse;
import com.global.project.modal.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    AccountResponse registerAccount(SignupRequest signupRequest);
    ResponseEntity<ApiResponse<AccountResponse>> getMe();
}
