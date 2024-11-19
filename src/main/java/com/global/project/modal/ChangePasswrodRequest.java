package com.global.project.modal;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswrodRequest {
    String newPasswrod;
    String token;
}
