package com.sprta.newsfeed.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
}