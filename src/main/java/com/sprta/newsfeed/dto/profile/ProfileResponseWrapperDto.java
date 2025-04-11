package com.sprta.newsfeed.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponseWrapperDto {
    private boolean success;
    private String message;
    private ProfileResponseDto data;

    // 성공 응답
    public static ProfileResponseWrapperDto success(String message, ProfileResponseDto data) {
        return new ProfileResponseWrapperDto(true, message, data);
    }

    // 실패 응답
    public static ProfileResponseWrapperDto fail(String message) {
        return new ProfileResponseWrapperDto(false, message, null);
    }
}
