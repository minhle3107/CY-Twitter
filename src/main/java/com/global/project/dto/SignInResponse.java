package com.global.project.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class SignInResponse {
    private Long id;
    private String type = "Bearer";
    private String accountToken;
    private String refreshToken;
    private String username;
    private String email;
    private Boolean isActive;
    private String avatar;
    private String roleName;
}
