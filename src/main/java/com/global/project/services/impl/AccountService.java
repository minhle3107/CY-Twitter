package com.global.project.services.impl;

import com.global.project.configuration.AccountDetailsImpl;
import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.AccountResponse;
import com.global.project.dto.ApiResponse;
import com.global.project.entity.Account;
import com.global.project.entity.User;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.AccountMapper;
import com.global.project.modal.SignupRequest;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.RoleRepository;
import com.global.project.repository.UserRepository;
import com.global.project.services.IAccountService;
import com.global.project.utils.Const;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService implements IAccountService, UserDetailsService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    AccountMapper accountMapper;
    JwtProvider _jwtProvider;

//    public AccountService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//        User user = User.builder()
//                .username("admin")
//                .build();
//        userRepository.saveAndFlush(user);
//
//        Account checkExsit = accountRepository.findByUser_Username("admin").orElse(null);
//        if (checkExsit == null) {
//            Account account = new Account();
//            account.setEmail("admin");
//            account.setUser(user);
//            account.setIsActive(true);
//            account.setEmail("test@gmail.com");
//
//            Role role = roleRepository.findByName(Const.ROLE_ADMIN);
//            if (role == null) {
//                role = new Role();
//                role.setName(Const.ROLE_ADMIN);
//                roleRepository.saveAndFlush(role);
//            }
//            account.setRole(role);
//            account.setPassword(passwordEncoder.encode("admin"));
//            accountRepository.save(account);
//        }
//        Role roleAdmin = roleRepository.findByName(Const.ROLE_ADMIN);
//        if (roleAdmin == null) {
//            roleAdmin = new Role();
//            roleAdmin.setName(Const.ROLE_ADMIN);
//            roleRepository.saveAndFlush(roleAdmin);
//        }
//        Role roleUser = roleRepository.findByName(Const.ROLE_USER);
//        if (roleUser == null) {
//            roleUser = new Role();
//            roleUser.setName(Const.ROLE_USER);
//            roleRepository.saveAndFlush(roleUser);
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AccountDetailsImpl(accountRepository.findByUser_Username(username).get());
    }

    @Override
    public AccountResponse registerAccount(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_USERNAME_EXISTED);
        }
        if (accountRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AppException(ErrorCode.ACCOUNT_EMAIL_EXISTED);
        }

        User user = User.builder()
                .username(signupRequest.getUsername())
                .name(signupRequest.getName())
                .gender(signupRequest.getGender())
                .dob(signupRequest.getDob())
                .isActive(true)
                .build();

        User insertUser = userRepository.saveAndFlush(user);

        if (insertUser == null) {
            throw new AppException(ErrorCode.USER_CANT_CREATE_USER);
        }
        Account account = Account.builder()
                .user(insertUser)
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .email(signupRequest.getEmail())
                .role(roleRepository.findByName(Const.ROLE_USER))
                .isActive(true)
                .build();

        return accountMapper.toResponse(accountRepository.saveAndFlush(account));
    }

    @Override
    public ResponseEntity<ApiResponse<AccountResponse>> getMe() {
        try {
            String bearerToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest().getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);
                Long userId = _jwtProvider.getUserIdFromJWT(token);
                Optional<Account> accountOpt = accountRepository.findById(userId);
                if (accountOpt.isPresent()) {
                    Account account = accountOpt.get();
                    AccountResponse accountResponse = accountMapper.toResponse(account);
                    return ResponseEntity.ok(ApiResponse.<AccountResponse>builder()
                            .message("Successfully.")
                            .data(accountResponse)
                            .build());
                } else {
                    throw new AppException(ErrorCode.USER_NOT_FOUND);
                }
            } else {
                throw new AppException(ErrorCode.INVALID_MISSING_TOKEN);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AccountResponse>builder()
                            .message("An error occurred while retrieving account details.")
                            .build());
        }
    }


}
