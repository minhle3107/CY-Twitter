package com.global.project.repository;

import com.global.project.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query(value = "select  coalesce(count(l), 0) from Like l where l.tweetId = :tweetId")
    Long countLike(@Param("tweetId") Long tweetId);

    @Query(value = "select coalesce(count(b),0) from Bookmark b where b.tweetId = :tweetId")
    Long countBookMark(@Param("tweetId") Long tweetId);

    @Query(value = "select coalesce(count(tw),0) from Tweet tw where tw.id =:tweetId and tw.type = :type")
    Long countByTypeTweet(@Param("tweetId") Long tweetId, @Param("type") Long type);

}
