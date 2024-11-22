package com.global.project.utils;

import com.global.project.configuration.jwtConfig.JwtProvider;
import com.global.project.dto.SignInResponse;
import com.global.project.entity.Account;
import com.global.project.entity.Role;
import com.global.project.entity.User;
import com.global.project.exception.AppException;
import com.global.project.exception.ErrorCode;
import com.global.project.mapper.AccountMapper;
import com.global.project.modal.ModelAccountGoogle;
import com.global.project.repository.AccountRepository;
import com.global.project.repository.RoleRepository;
import com.global.project.repository.UserRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginHelper {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    JwtProvider jwtProvider;
    RoleRepository roleRepository;
    AccountMapper accountMapper;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUrl;

    private String getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", redirectUrl);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        String response = restTemplate.postForObject(url, requestEntity, String.class);
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        return jsonObject.get("access_token").toString().replace("\"", "");
    }

    public SignInResponse processGrantCode(String code) {
        String accessToken = getOauthAccessTokenGoogle(code);

        ModelAccountGoogle modelAccountGoogle = getProfileDetailsGoogle(accessToken);
        Optional<Account> account = accountRepository.findByEmail(modelAccountGoogle.getEmail());

        if (account.isPresent()) {
            Account acc = account.get();
            String accountToken = jwtProvider.generateTokenByUsername(acc.getUser().getUsername());
            String refreshToken = jwtProvider.generateRefreshTokenByUsername(LocalDateTime.now(), account.get().getUser().getUsername());

            return SignInResponse.builder()
                    .id(acc.getId())
                    .type("Bearer")
                    .accountToken(accountToken)
                    .refreshToken(refreshToken)
                    .username(acc.getUser().getUsername())
                    .email(acc.getEmail())
                    .isActive(acc.getIsActive())
                    .avatar(acc.getUser().getAvatar())
                    .roleName(acc.getRole().getName())
                    .build();

        }

        Account accountRegister = registerAccount(modelAccountGoogle);
        String accountToken = jwtProvider.generateTokenByUsername(accountRegister.getUser().getUsername());
        String refreshToken = jwtProvider.generateRefreshTokenByUsername(LocalDateTime.now(), accountRegister.getUser().getUsername());

        return SignInResponse.builder()
                .id(accountRegister.getId())
                .type("Bearer")
                .accountToken(accountToken)
                .refreshToken(refreshToken)
                .username(accountRegister.getUser().getUsername())
                .email(accountRegister.getEmail())
                .isActive(accountRegister.getIsActive())
                .avatar(accountRegister.getUser().getAvatar())
                .roleName(accountRegister.getRole().getName())
                .build();

    }

    private ModelAccountGoogle getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        JsonObject jsonObject = new Gson().fromJson(response.getBody(), JsonObject.class);

        return ModelAccountGoogle.builder()
                .name(jsonObject.get("email").toString().replace("\"", ""))
                .username(jsonObject.get("name").toString().replace("\"", ""))
                .email(jsonObject.get("email").toString().replace("\"", ""))
                .password(UUID.randomUUID().toString())
                .avatar(jsonObject.get("picture").toString().replace("\"", ""))
                .dob(LocalDate.now())
                .gender(1)
                .build();

    }

    public Account registerAccount(ModelAccountGoogle modelAccountGoogle) {
        if (userRepository.existsByUsername(modelAccountGoogle.getUsername())) {
            throw new AppException(ErrorCode.USER_USERNAME_EXISTED);
        }
        if (accountRepository.existsByEmail(modelAccountGoogle.getEmail())) {
            throw new AppException(ErrorCode.ACCOUNT_EMAIL_EXISTED);
        }

        User user = User.builder()
                .username(modelAccountGoogle.getUsername())
                .name(modelAccountGoogle.getName())
                .gender(modelAccountGoogle.getGender())
                .dob(modelAccountGoogle.getDob())
                .avatar(modelAccountGoogle.getAvatar())
                .isActive(true)
                .avatar("images/cd207dcb-364f-4251-a9d2-8864719bf42f_img2.jpg")
                .build();

        User insertUser = userRepository.saveAndFlush(user);

        if (insertUser == null) {
            throw new AppException(ErrorCode.USER_CANT_CREATE_USER);
        }

        Role role = roleRepository.findByName(Const.ROLE_USER);
        if (role == null) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }

        Account account = Account.builder()
                .user(insertUser)
                .password(passwordEncoder.encode(modelAccountGoogle.getPassword()))
                .email(modelAccountGoogle.getEmail())
                .role(role)
                .isActive(true)
                .build();
        return accountRepository.saveAndFlush(account);
    }
}
