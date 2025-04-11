package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // 논리적으로 삭제되지 않은 동일 이메일 존재 여부 확인
    // 회원가입 시 중복 이메일 검증에 사용
    boolean existsByEmailAndIsDeletedFalse(String email);

    // 삭제된 사용자 중 동일 이메일이 존재하는지 확인
    boolean existsByEmailAndIsDeletedTrue(String email);

    Long id(Long id);

}
