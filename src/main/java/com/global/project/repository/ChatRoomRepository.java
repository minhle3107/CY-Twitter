package com.global.project.repository;

import com.global.project.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderUsernameAndReceiveUsername(String senderUsername, String receiveUsername);


    @Query("SELECT c FROM ChatRoom c WHERE c.senderUsername = :username ORDER BY c.id DESC")
    List<ChatRoom> findBySenderUsername(@Param("username") String username);
}