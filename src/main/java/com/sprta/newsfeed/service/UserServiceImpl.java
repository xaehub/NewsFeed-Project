package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.dto.Signup.SignupResponseDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.sprta.newsfeed.security.customerror.ErrorCode.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository; // 사용자 저장소 (DB 연동)
    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 생성자 주입
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SignupResponseDto signUp(String userName, String email, String password) {

        // 탈퇴한 계정인지 먼저 확인
        if (userRepository.existsByEmailAndIsDeletedTrue(email)) {
            throw new CustomException(ErrorCode.WITHDRAWN_EMAIL_REUSE_NOT_ALLOWED);
        }

        // 정상 계정과 중복 여부 확인
        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 엔티티 생성 및 저장
        User user = new User(userName, email, encodedPassword);
        User savedUser = userRepository.save(user);

        // 응답용 DTO 반환
        return new SignupResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail());

    }

    @Override
    public LoginResponseDto login(String email, String password) {

        // 이메일로 사용자 조회
         User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        // 로그인 성공 → 응답 DTO 생성
        return new LoginResponseDto(user.getId(), user.getEmail());
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 세션 무효화 → 로그아웃
        }
    }

    @Override
    @Transactional
    public void signout(Long userId, String password) {

        // 사용자 ID로 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 회원 탈퇴를 진행한 user 인지 확인
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_ALREADY_DELETED);
        }

        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
        }

        // 논리 삭제 처리
        user.markAsDeleted();  // 내부적으로 isDeleted, deletedAt 동시 처리
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

}
