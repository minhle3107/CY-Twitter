package com.global.project.repository;

import com.global.project.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookMarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findLikeByUsernameAndTweetId(String username, Long tweetId);
}
