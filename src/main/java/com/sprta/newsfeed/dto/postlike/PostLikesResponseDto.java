package com.sprta.newsfeed.dto.postlike;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PostLikesResponseDto {
    private final String message;

    public PostLikesResponseDto(String message) {
        this.message = message;
    }
}
