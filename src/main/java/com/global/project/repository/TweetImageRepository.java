package com.global.project.repository;

import com.global.project.entity.TweetImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetImageRepository extends JpaRepository<TweetImage, Long> {
}
