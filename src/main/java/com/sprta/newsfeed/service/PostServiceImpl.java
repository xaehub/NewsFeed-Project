package com.sprta.newsfeed.service;


import com.sprta.newsfeed.dto.Post.PostCreateRequestDto;
import com.sprta.newsfeed.dto.Post.PostResponseDto;
import com.sprta.newsfeed.dto.Post.PostUpdateRequestDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.*;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor //final로 선언된 필드에 대해 생성자 자동생성
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostLikesRepository postLikesRepository;
    private final FollowRepository followRepository;

    @Override
    //게시글 작성 로직
    public PostResponseDto createPost(PostCreateRequestDto requestDto, Long loginUserId) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), user);
        Post saved = postRepository.save(post);

        Integer likeCount = Math.toIntExact(postLikesRepository.countByPost(post));
        return new PostResponseDto(
                saved.getId(),
                saved.getUser().getUserName(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCountComments(),
                likeCount
        );
    }

    @Override
    @Transactional
    //게시글 수정 로직
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto, String email) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.FORBIDDEN_UPDATE);
        }

        post.updateContent(requestDto.getContent());

        Integer likeCount = Math.toIntExact(postLikesRepository.countByPost(post));

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContent(),
                post.getCountComments(),
                likeCount
        );
    }

    @Override
    //게시글 조회 로직
    public List<PostResponseDto> getAllPosts(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.stream().map(post -> {
            Integer likeCount = Math.toIntExact(postLikesRepository.countByPost(post));
            return new PostResponseDto(
                    post.getId(),
                    post.getUser().getUserName(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCountComments(),
                    likeCount
            );
        }).toList();
    }

    @Override
    // 게시글 + 댓글 조회
    public PostResponseDto getPostWithComments(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByPostId(id);
        Integer likeCount = Math.toIntExact(postLikesRepository.countByPost(post));

        return new PostResponseDto(post, comments, likeCount);
    }

    //게시글 삭제
    @Override
    public void deletePost(Long id, String email) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.FORBIDDEN_DELETE);
        }

        postRepository.delete(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));  // 게시물이 없으면 예외 던짐
    }

    public List<PostResponseDto> getTimelinePosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Post> posts = postRepository.findAllWithFollowPriority(user);

        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getUser().getUserName(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCountComments(),
                        Math.toIntExact(postLikesRepository.countByPost(post))
                ))
                .toList();
    }
}
