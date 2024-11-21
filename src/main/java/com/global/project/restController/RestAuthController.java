package com.global.project.restController;

import com.global.project.dto.ApiResponse;
import com.global.project.entity.Account;
import com.global.project.modal.ChangePasswrodRequest;
import com.global.project.modal.LogoutRequest;
import com.global.project.modal.SignInRequest;
import com.global.project.modal.SignupRequest;
import com.global.project.repository.AccountRepository;
import com.global.project.services.IAccountService;
import com.global.project.services.IAuthService;
import com.global.project.services.IAuthenticationService;
import com.global.project.utils.Const;
import com.global.project.utils.LoginHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
    IAuthenticationService authenticationService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IAuthService iAuthService;
    @Autowired
    LoginHelper loginHelper;


    @Operation(summary = "signin", description = "singin to system", tags = {"01. AUTH"})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest, HttpServletRequest request) {
        return iAuthService.login(signInRequest, request);
    }

    @Operation(summary = "logout", description = "logout to system", tags = {"01. AUTH"})
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest signInRequest) {
        return iAuthService.logout(signInRequest);
    }


    @PostMapping("/via-email")
    public ApiResponse<?> viaEmailToResgister(@RequestBody String email) {
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

    @GetMapping("/forgot-password")
    public ApiResponse<?> forgotPassword(@RequestParam String email) throws MessagingException {
        return ApiResponse.builder()
                .data(authenticationService.sendTokenForgotPassword(email))
                .message("send token to reset password successfully")
                .build();
    }

    @PostMapping("/forot-password")
    public ApiResponse<?> changePassword(@RequestBody ChangePasswrodRequest changePasswrodRequest) {
        return ApiResponse.builder()
                .data(authenticationService.resetPassword(changePasswrodRequest))
                .message("change password successfully")
                .build();
    }

    @GetMapping("/oauth/google")
    public ApiResponse<?> hanldeLoginWithGoogle(@RequestParam("code") String code,
                                                @RequestParam("scope") String scope,
                                                @RequestParam("authuser") String authUser,
                                                @RequestParam("prompt") String prompt) {
        return ApiResponse.builder()
                .data(loginHelper.processGrantCode(code))
                .build();
    }

}
