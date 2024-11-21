package com.global.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_rooms", indexes = {
        @Index(name = "idx_sender_username", columnList = "sender_username"),
        @Index(name = "idx_receive_username", columnList = "receive_username")
})
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_username", nullable = false)
    private String senderUsername;

    @Column(name = "receive_username", nullable = false)
    private String receiveUsername;

}