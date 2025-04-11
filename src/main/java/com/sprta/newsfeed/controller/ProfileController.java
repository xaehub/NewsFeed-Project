package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.profile.ProfileResponseDto;
import com.sprta.newsfeed.dto.profile.ProfileResponseWrapperDto;
import com.sprta.newsfeed.dto.profile.ProfileUpdateRequestDto;
import com.sprta.newsfeed.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 작성
     *
     * @param request    세션를 통해 사용자 정보를 담고 있는 HTTP 요청 객체
     * @param requestDto 사용자가 입력한 프로필 정보
     * @return 작성 완료 메시지
     */
    @PostMapping
    public ResponseEntity<ProfileResponseWrapperDto> createProfile(
            HttpServletRequest request,
            @RequestBody @Valid ProfileUpdateRequestDto requestDto
    ) {
        profileService.createProfile(request, requestDto);
        return ResponseEntity.ok(ProfileResponseWrapperDto.success("프로필이 성공적으로 작성되었습니다.", null));
    }

    /**
     * 프로필 조회
     *
     * @param request 사용자 정보를 담고 있는 HTTP 요청 객체
     * @return 프로필 정보를 담은 응답 DTO (성공 여부, 메시지, 프로필 데이터 )
     */
    @GetMapping
    public ResponseEntity<ProfileResponseWrapperDto> getProfile(HttpServletRequest request) {
        ProfileResponseDto dto = profileService.getProfile(request);
        return ResponseEntity.ok(ProfileResponseWrapperDto.success("프로필 조회 성공", dto));
    }

    /**
     * 프로필 수정 API
     *
     * @param request    사용자 정보를 담고 있는 HTTP 요청 객체
     * @param requestDto 수정할 프로필 정보
     * @return 수정된 프로필 정보를 포함한 응답 DTO
     */
    @PutMapping
    public ResponseEntity<ProfileResponseWrapperDto> updateProfile(
            HttpServletRequest request,
            @RequestBody @Valid ProfileUpdateRequestDto requestDto
    ) {
        ProfileResponseDto updatedDto = profileService.updateProfile(request, requestDto);
        return ResponseEntity.ok(ProfileResponseWrapperDto.success("프로필이 수정되었습니다.", updatedDto));
    }
}

