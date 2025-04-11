package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.*;
import com.sprta.newsfeed.dto.Login.LoginRequestDto;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.dto.Signup.SignupRequestDto;
import com.sprta.newsfeed.dto.Signup.SignupResponseDto;
import com.sprta.newsfeed.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API 컨트롤러로 등록
@RequestMapping("/api") // 이 컨트롤러의 기본 URI 경로 설정
@RequiredArgsConstructor // 생성자 주입을 자동으로 생성 (final 필드 대상)
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto =
                userService.signUp(
                        requestDto.getUserName(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletRequest request) {

        // 기존 세션이 존재하고 로그인된 유저가 있다면 로그인 차단
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute(Const.LOGIN_USER) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("이미 로그인되어 있습니다. 로그아웃 후 다시 시도해주세요.");
        }

        // 사용자 인증
        LoginResponseDto login = userService.login(requestDto.getEmail(), requestDto.getPassword());

        // 새로운 세션 생성 및 로그인 유저 정보 저장
        HttpSession session = request.getSession(true);
        // 로그인 성공 시 사용자 정보를 세션에 저장
        session.setAttribute(Const.LOGIN_USER, login);

        return ResponseEntity.ok("로그인 되었습니다");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 세션 무효화 등의 로직은 userService.logout() 내부에서 처리
        userService.logout(request);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }


    @DeleteMapping("/signout")
    public ResponseEntity<String> signout(@RequestBody DeleteRequestDto requestDto,
                                         HttpServletRequest request) {

        // 기존 세션 가져오기 (없으면 null)
        HttpSession session = request.getSession(false);

        // 세션에 저장된 로그인 유저 정보 꺼내기
        LoginResponseDto responseDto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);

        // userId와 비밀번호를 전달하여 탈퇴 처리
        userService.signout(responseDto.getUserId(), requestDto.getPassword());

        // 탈퇴 완료 후 세션 무효화 → 자동 로그아웃 처리
        session.invalidate();

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
