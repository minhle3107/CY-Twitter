package com.global.project.modal;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenModel {

    private Long accountId;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime iat;

    private LocalDateTime exp;


}
