package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN Follow f ON f.following = p.user AND f.follower = :user " +
            "ORDER BY CASE WHEN f.id IS NOT NULL THEN 0 ELSE 1 END, p.createdAt DESC")
    List<Post> findAllWithFollowPriority(@Param("user") User user);
}
