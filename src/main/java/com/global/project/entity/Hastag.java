package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hastags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hastag {
    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}