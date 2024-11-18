package com.global.project.restController;

import com.global.project.configuration.AccountDetailsImpl;
import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.entity.Account;
import com.global.project.modal.SigninRequest;
import com.global.project.modal.SigninResponse;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.RoleRepository;
import com.global.project.services.IAccountService;
import com.global.project.utils.Const;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01. AUTH")
@RestController
@RequestMapping(value = Const.PREFIX_VERSION + "/auth")
public class RestAuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtUtils;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Operation(summary = "signin", description = "singin to system", tags = {"01. AUTH"})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();

        String accountToken = jwtUtils.generateTokenByUsername(accountDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshTokenByUsername(accountDetails.getUsername());
        return ResponseEntity.ok(new SigninResponse(accountDetails.getAccount().getId(), "Bearer", accountToken, refreshToken, accountDetails.getUsername(), accountDetails.getAccount().getEmail(),
                accountDetails.getAccount().getIsActive(), accountDetails.getRoleName()));
    }

    @Operation(summary = "reset pass admin", description = "reset pass admin to admin", tags = {"01. AUTH"})
    @GetMapping("/resetPassAdmin")
    public String resetPassAdmin() {
        Account account = accountRepository.findByUsername("admin").orElse(null);
        account.setPassword(passwordEncoder.encode("admin"));
        accountRepository.save(account);
        return "";
    }
}
