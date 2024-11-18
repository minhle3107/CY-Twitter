package com.global.project.modal;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class SigninResponse {
    private Long id;
    private String type = "Bearer";
    private String accountToken;
    private String refreshToken;
    private String username;
    private String email;
    private Boolean isActive;
    private String roleName;
}
