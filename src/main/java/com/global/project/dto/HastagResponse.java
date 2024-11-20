package com.global.project.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class HastagResponse {
    private String name;
    private LocalDateTime createdAt;
}
