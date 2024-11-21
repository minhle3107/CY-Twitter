package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_rooms", indexes = {
        @Index(name = "idx_sender_username", columnList = "sender_username"),
        @Index(name = "idx_receive_username", columnList = "receive_username")
})
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "sender_username", nullable = false)
    private String senderUsername;

    @Size(max = 255)
    @Column(name = "receive_username", nullable = false)
    private String receiveUsername;
}