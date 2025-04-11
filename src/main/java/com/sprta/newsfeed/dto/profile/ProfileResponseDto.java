package com.sprta.newsfeed.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class ProfileResponseDto {
    private final String name;
    private final String email;
    private final String introduction;

    public ProfileResponseDto(String name, String email, String introduction) {
        this.name = name;
        this.email = email;
        this.introduction = introduction;
    }
}
