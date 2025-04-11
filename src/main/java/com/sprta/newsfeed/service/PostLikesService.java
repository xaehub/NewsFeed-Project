package com.sprta.newsfeed.service;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.PostLikes;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.PostLikesRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final UserRepository userRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    // 로그인한 사용자 정보 가져오기
    private User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            // 세션에 로그인 정보가 없으면 예외 처리
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        // 세션에 있는 userID로 유저 정보 조회
        return userRepository.findById(loginUser.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
    }

    // 게시글 좋아요
    public void likePost(HttpServletRequest request, Long postId) {
        User user = getLoginUser(request); // 로그인한 사용자 가져오기

        // 게시글 ID로 게시글 찾고 없으면 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 사용자가 게시글에 이미 좋아요를 눌렀는지 확인
        if (postLikesRepository.existsByUserAndPost(user, post)) {
            throw new CustomException(ErrorCode.POST_ALREADY_LIKED); // 중복 좋아요 방지
        }

        // 좋아요 저장
        postLikesRepository.save(new PostLikes(user, post));
    }


    // 좋아요 취소
    public void unlikePost(HttpServletRequest request, Long postId) {
        User user = getLoginUser(request); // 로그인한 사용자 가져오기

        // 게시글 ID로 게시글 찾고 없으면 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 사용자가 게시글에 좋아요를 눌렀는지 확인
        PostLikes postLike = postLikesRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        // 좋아요 삭제
        postLikesRepository.delete(postLike);
    }
}
