package com.sprta.newsfeed.controller;


import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Post.PostCreateRequestDto;
import com.sprta.newsfeed.dto.Post.PostResponseDto;
import com.sprta.newsfeed.dto.Post.PostUpdateRequestDto;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    //로그인 유저 정보 받아와서 게시글 작성
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto requestDto,
                                                      @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto) {

        PostResponseDto response = postService.createPost(requestDto, dto.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id,
                                           @RequestBody PostUpdateRequestDto requestDto,
                                           @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser) {
        postService.updatePost(id, requestDto,loginUser.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //게시글 페이지 조회 (api/posts?page="원하는 페이지,0부터 시작임")
    @GetMapping
    public List<PostResponseDto> getAllPost(@RequestParam int page) {
        return postService.getAllPosts(page);
    }

    //게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        PostResponseDto response = postService.getPostWithComments(id);
        return ResponseEntity.ok(response);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,
                                             @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser) {
        postService.deletePost(id, loginUser.getEmail());
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    //내가 팔로우 한 사람들의 게시글 조회
    @GetMapping("/followings")
    public List<PostResponseDto> getFollowedUsersPosts(@SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser) {
        return postService.getTimelinePosts(loginUser.getUserId());
    }

}
