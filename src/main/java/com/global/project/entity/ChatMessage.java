package com.global.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_messages", indexes = {
        @Index(name = "idx_sender_username", columnList = "sender_username"),
        @Index(name = "idx_receive_username", columnList = "receive_username")
})
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Size(max = 255)
    @Column(name = "sender_username", nullable = false)
    private String senderUsername;

    @Size(max = 255)
    @Column(name = "receive_username", nullable = false)
    private String receiveUsername;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

}