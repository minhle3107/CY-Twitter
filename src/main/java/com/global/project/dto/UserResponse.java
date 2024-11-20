package com.global.project.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String name;
    private LocalDate dob;
    private String avatar;
    private String coverPhoto;
    private int gender;
    private String location;
    private String email;
    private String website;
    private String bio;
    String role;
}
