package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.dto.Signup.SignupResponseDto;
import com.sprta.newsfeed.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    //회원가입 기능
    SignupResponseDto signUp(String userName, String email, String password);

    //로그인 기능
    LoginResponseDto login(String email, String password);

    //로그아웃 기능
    void logout(HttpServletRequest request);

    //회원 탈퇴 기능
    void signout(Long userId, String inputPassword);

    //사용자 조회
    User findById(Long userId);
}

