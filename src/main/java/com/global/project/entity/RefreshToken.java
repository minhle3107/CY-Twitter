package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "account_id")
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

}