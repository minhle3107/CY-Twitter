package com.global.project.repository;

import com.global.project.entity.TweetMention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetMentionRepository extends JpaRepository<TweetMention, Long> {
}
