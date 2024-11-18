package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_device_info", columnList = "device_info"),
        @Index(name = "idx_token", columnList = "token")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Size(max = 255)
    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "iat")
    private LocalDateTime iat;

    @Column(name = "exp")
    private LocalDateTime exp;

    @Size(max = 255)
    @Column(name = "device_info")
    private String deviceInfo;
}