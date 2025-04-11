package com.sprta.newsfeed.dto.Post;


import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostResponseDto {

    private final Long id;

    private final String userName;

    private final String title;

    private final String content;

    private final Integer commentCount;

    private final Integer likeCount;

    private final List<CommentResponseDto> comments;// 댓글 리스트 추가

    //댓글 없이 게시글만 응답할 때
    public PostResponseDto(Long id, String userName, String title, String content, Integer commentCount, Integer likeCount) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.comments = new ArrayList<>();
    }

    //게시글 + 댓글 응답할 때
    public PostResponseDto(Post post, List<Comment> comments, Integer likeCount){
        this.id = post.getId();
        this.userName = post.getUser().getUserName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentCount = post.getCountComments();
        this.likeCount = likeCount;
        this.comments = comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
