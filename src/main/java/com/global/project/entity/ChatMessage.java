package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_messages", indexes = {
        @Index(name = "idx_sender_username", columnList = "sender_username"),
        @Index(name = "idx_receive_username", columnList = "receive_username")
})
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name = "sender_username", nullable = false)
    private String senderUsername;

    @Column(name = "receive_username", nullable = false)
    private String receiveUsername;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}