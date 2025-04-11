package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.service.CommentLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comment/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikesController {

    private final CommentLikesService commentLikesService;

    // 1. 좋아요 누르기
    @PostMapping
    public ResponseEntity<Void> addLike(

            @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto, // 로그인 유저 정보
            @PathVariable Long postId, // 해당 댓글이 있는 게시물 id
            @PathVariable Long commentId) { // 좋아요를 누를 댓글 id

        commentLikesService.saveLike(dto.getUserId(), postId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 2. 좋아요 취소하기
    @DeleteMapping
    public ResponseEntity<Void> deleteLike(
            @SessionAttribute(name = Const.LOGIN_USER)LoginResponseDto dto,  // 로그인 유저 정보
            @PathVariable Long postId, // 해당 댓글이 있는 게시물 id
            @PathVariable Long commentId) { // 좋아요를 누를 댓글 id

        commentLikesService.deleteLike(dto.getUserId(), postId, commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
