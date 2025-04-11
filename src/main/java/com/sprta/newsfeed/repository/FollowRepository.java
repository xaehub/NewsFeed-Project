package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Follow;
import com.sprta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // DB에 이미 팔로우한 사람이 있는지 찾기
    boolean existsByFollowerAndFollowing(User Follower, User Following);

    Optional<Follow> findByFollowerAndFollowing(User Follower, User Following);

    Integer countByFollowing(User user); // 내가 팔로우 하는 사람

    Integer countByFollower(User user); // 나를 팔로우 하는 사람

}
