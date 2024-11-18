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
@Table(name = "via_codes")
public class ViaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "via_code")
    private Integer viaCode;

    @Size(max = 200)
    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}