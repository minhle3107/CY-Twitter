package com.global.project.entity;

import com.global.project.enums.NotificationStatus;
import com.global.project.enums.TypeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "actor")
    private String actor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeStatus type;

    @Lob
    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;

}