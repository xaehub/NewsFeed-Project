package com.sprta.newsfeed.service;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.dto.profile.ProfileResponseDto;
import com.sprta.newsfeed.entity.Profile;
import com.sprta.newsfeed.dto.profile.ProfileUpdateRequestDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.ProfileRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 일치 여부 확인


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

    // 프로필 생성
    public void createProfile(HttpServletRequest request, ProfileUpdateRequestDto requestDto) {
        User user = getLoginUser(request); // 로그인한 user정보 가져오기

        // 이미 프뢸이 있으면 더 이상 만들 수 없음
        if (profileRepository.existsByUserId(user.getId())) {
            throw new CustomException(ErrorCode.PROFILE_ALREADY_EXISTS);
        }

        // 새로운 프로필 객체를 만들어서 저장
        Profile profile = new Profile(user, requestDto.getIntroduction());

        try {
            profileRepository.save(profile);
        } catch (Exception e) {
            // 저장하다 에러 나면 예외 발생
            throw new CustomException(ErrorCode.PROFILE_CREATION_FAILED);
        }
    }

    // 프로필 조회
    public ProfileResponseDto getProfile(HttpServletRequest request) {
        User user = getLoginUser(request); // 로그인한 user정보 가져오기

        // 사용자 ID로 프로필을 찾고 없으면 예외 던지기
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        // 사용자 이름, 이메일, 소개글을 담아서 리턴
        return new ProfileResponseDto(user.getUserName(), user.getEmail(), profile.getIntroduction());
    }

    // 프로필 수정
    public ProfileResponseDto updateProfile(HttpServletRequest request, ProfileUpdateRequestDto requestDto) {
        User user = getLoginUser(request); // 로그인한 user정보 가져오기

        // 사용자가 입력한 비밀번화 실제 비밀번호가 같은지 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD); // 비밀번호 틀리면 예외
        }

        // 기존 프로필을 찾고 없으면 예외 던지기
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));

        // 소개글을 새로운 내용으로 바꾸고 저장
        profile.updateIntroduction(requestDto.getIntroduction());
        profileRepository.save(profile);

        // 업데이트된 프로필 정보를 리턴
        return new ProfileResponseDto(user.getUserName(), user.getEmail(), profile.getIntroduction());
    }

}
