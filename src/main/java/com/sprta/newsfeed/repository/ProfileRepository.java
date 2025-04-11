package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId); // userid로 프로필 조회
    boolean existsByUserId(Long userId);
}