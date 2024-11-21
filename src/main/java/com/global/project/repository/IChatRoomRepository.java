package com.global.project.repository;

import com.global.project.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c WHERE (c.senderUsername = :username1 AND c.receiveUsername = :username2) OR (c.senderUsername = :username2 AND c.receiveUsername = :username1)")
    ChatRoom findByUsers(@Param("username1") String username1, @Param("username2") String username2);

}