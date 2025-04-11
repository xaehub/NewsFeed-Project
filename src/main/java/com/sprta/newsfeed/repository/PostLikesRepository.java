package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    // user가 해당 게시글에 좋아요를 눌렀는지 확인
    boolean existsByUserAndPost(User user, Post post);

    // user가 누른 좋아요 찾기
    Optional<PostLikes> findByUserAndPost(User user, Post post);

    // 특정 게시글의 좋아요 수
    Long countByPost(Post post);
}