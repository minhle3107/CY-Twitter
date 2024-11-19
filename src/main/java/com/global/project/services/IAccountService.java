package com.global.project.services;

import com.global.project.dto.AccountResponse;
import com.global.project.modal.SignupRequest;

public interface IAccountService {
    AccountResponse registerAccount(SignupRequest signupRequest);
}
