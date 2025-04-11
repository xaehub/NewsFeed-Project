package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "users")
public class User {

    @Id // 기본 키(primary key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // null을 허용하지 않는 일반 컬럼
    private String userName;

    @Column(nullable = false, unique = true) // null 허용 X, 이메일 중복 방지
    private String email;

    @Column(nullable = false) // 비밀번호도 null 불가
    private String password;


    // 사용자가 누른 좋아요 목록 관리, 사용자가 눌렀던 모든 좋아요도 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLikes> postLikes = new ArrayList<>();
    
    // 논리적 삭제를 위한 플래그, 실제 삭제하지 않고 true로 표시
    private boolean isDeleted = false; 

    // 삭제 시각 저장용 필드
    private LocalDateTime deletedAt;

    // JPA를 위한 기본 생성자
    public User() {
    }

    // 사용자 정보를 초기화하는 생성자
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;

    }

    // 사용자를 논리적으로 삭제 처리하는 메서드
    public void markAsDeleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // 삭제 여부 반환 메서드
    public boolean isDeleted() {
        return isDeleted;
    }

    // 비밀번호 변경
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
