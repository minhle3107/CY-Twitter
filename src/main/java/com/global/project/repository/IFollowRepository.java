package com.global.project.repository;

import com.global.project.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFollowRepository extends JpaRepository<Follower,Long> {
        Follower findByUsernameAndFollowedUsername(String username, String followed_username);
}
