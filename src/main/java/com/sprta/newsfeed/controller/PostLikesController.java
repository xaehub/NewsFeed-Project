package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.repository.PostLikesRepository;
import com.sprta.newsfeed.service.PostLikesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/like")
public class PostLikesController {

    private final PostLikesRepository postLikesRepository;
    private final PostLikesService postLikesService;

    /**
     * 게시글 좋아요
     *
     * @param postId 좋아요를 누를 게시글의 ID
     * @param request 사용자 세션 정보를 담고 있는 HttpServletRequest
     * @return 성공 시 "게시글 좋아요 완료" 메시지 반환
     */
    @PostMapping("{postId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId,
            HttpServletRequest request
    ) {
        postLikesService.likePost(request, postId);
        return ResponseEntity.ok("게시글 좋아요 완료");
    }

    /**
     * 게시글 좋아요 취소
     *
     * @param postId  좋아요를 취소할 게시글의 ID
     * @param request 사용자 세션 정보를 담고 있는 HttpServletRequest
     * @return 성공 시 "게시글 좋아요 취소 완료" 메시지를 반환
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long postId,
            HttpServletRequest request
    ) {
        postLikesService.unlikePost(request, postId);
        return ResponseEntity.ok("게시글 좋아요 취소 완료");
    }
}
