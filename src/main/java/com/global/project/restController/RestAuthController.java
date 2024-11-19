package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.entity.Account;
import com.global.project.modal.SignInRequest;
import com.global.project.modal.SignupRequest;
import com.global.project.repository.AccountRepository;
import com.global.project.services.IAccountService;
import com.global.project.services.IAuthService;
import com.global.project.services.IAuthenticationService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01. AUTH")
@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/auth")
public class RestAuthController {
    @Autowired
    AccountRepository accountRepository;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    IAuthenticationService authenticationService;
    @Autowired
    IAccountService accountService;

    @Autowired
    IAuthService iAuthService;


    @Operation(summary = "signin", description = "singin to system", tags = {"01. AUTH"})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest, HttpServletRequest request) {
        return iAuthService.login(signInRequest, request);
    }

    @Operation(summary = "reset pass admin", description = "reset pass admin to admin", tags = {"01. AUTH"})
    @GetMapping("/resetPassAdmin")
    public String resetPassAdmin() {
        Account account = accountRepository.findByUser_Username("admin").orElse(null);
        account.setPassword(passwordEncoder.encode("admin"));
        accountRepository.save(account);
        return "";
    }

    @GetMapping("/via-email")
    public ApiResponse<?> viaEmailToResgister(@RequestParam String email) {
        return ApiResponse.builder()
                .data(authenticationService.handleSendCodeToMail(email))
                .message("Send code to maill successfulll")
                .build();
    }

    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(@RequestBody SignupRequest signupRequest) {
        return ApiResponse.builder()
                .data(accountService.registerAccount(signupRequest))
                .message("Successfully registered")
                .build();
    }

}
