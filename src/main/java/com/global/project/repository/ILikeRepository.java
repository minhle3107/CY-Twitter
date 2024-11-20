package com.global.project.repository;

import com.global.project.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Long> {
    Like findLikeByUsernameLikeAndTweetId(String username, Long tweetId);

}
