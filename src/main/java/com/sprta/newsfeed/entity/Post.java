package com.sprta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer countComments = 0;

    @Column(name = "likes_count", nullable = false)
    private Integer likesCount = 0;

    // 게시글 1개에 좋아요가 여러개 달리 수 있도록
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLikes> postLikes = new ArrayList<>();

    public Post(User user, String title, String content, Integer countComments, Integer likesCount) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.countComments = countComments;
        this.likesCount = likesCount;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.countComments = 0;
        this.likesCount = 0;
    }

    // 댓글 수 증가
    public void increaseCommentCount() {
        this.countComments += 1;
    }

    // 댓글 수 감소
    public void decreaseCommentCount() {
        this.countComments = Math.max(0, this.countComments - 1);
    }

}

