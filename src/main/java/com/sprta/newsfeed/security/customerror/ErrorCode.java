package com.sprta.newsfeed.security.customerror;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 서버
    INTERNAL_SERVER_ERROR(500, "SRV999", "서버 내부 오류입니다."),

    // 유저
    USER_NOT_FOUND(404, "USR001", "해당 유저를 찾을 수 없습니다."),

    // 게시물
    POST_NOT_FOUND(404, "PST001", "삭제되거나 존재하지 않는 게시물입니다."),
    POST_UPDATE_FAILED(400, "PST002", "게시물 수정에 실패했습니다."),
    POST_DELETE_FAILED(400, "PST003", "게시물 삭제에 실패했습니다."),
    POST_CREATION_FAILED(400, "PST004", "게시물 생성에 실패하셨습니다."),
    FORBIDDEN_DELETE(403, "PST005","게시글 삭제 권한이 없습니다."),
    FORBIDDEN_UPDATE(403, "PST006","게시글 수정 권한이 없습니다."),

    // 댓글
    COMMENT_NOT_FOUND(404, "CMT001", "해당 댓글을 찾을 수 없습니다."),
    COMMENT_CREATION_FAILED(400, "CMT002", "댓글 생성에 실패했습니다."),
    COMMENT_UPDATE_FAILED(400, "CMT003", "댓글 수정에 실패했습니다."),
    COMMENT_DELETE_FAILED(400, "CMT004", "댓글 삭제에 실패했습니다."),
    COMMENT_UPDATE_FORBIDDEN(403, "CMT005", "댓글 작성자만 수정할 수 있습니다."),
    COMMENT_DELETE_FORBIDDEN(403, "CMT006", "게시물 작성자 혹은 댓글 작성자만 삭제할 수 있습니다."),

    // 팔로우
    FOLLOW_NOT_FOUND(404, "FLW001", "팔로우 정보를 찾을 수 없습니다."),
    CANNOT_FOLLOW_SELF(400, "FLW002", "자기 자신은 팔로우할 수 없습니다."),
    ALREADY_FOLLOWING(409, "FLW003", "이미 팔로우한 사용자입니다."),
    NOT_FOLLOWING_USER(409, "FLW004", "해당 사용자를 팔로우하고 있지 않습니다."),

    // 프로필
    PROFILE_NOT_FOUND(404, "PRI001", "해당 사용자의 프로필을 찾을 수 없습니다."),
    PROFILE_CREATION_FAILED(400, "PRI002", "프로필 작성에 실패했습니다."),
    PROFILE_UPDATE_FAILED(400, "PRI003", "프로필 수정에 실패했습니다."),
    PROFILE_ALREADY_EXISTS(400, "PRI004", "이미 프로필이 존재합니다."),
    INVALID_PASSWORD_FORMAT(400, "PWD001", "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해 8자 이상이어야 합니다."),
    SAME_AS_OLD_PASSWORD(400, "PWD002", "현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다."),
    // 좋아요
    COMMENT_LIKE_NOT_FOUND(404, "C-LIK001", "해당 댓글에 좋아요 기록이 없습니다."),
    ALREADY_LIKE_COMMENT(409, "C-LIK002", "이미 해당 댓글에 좋아요를 했습니다."),

    //회원가입 / 회원 탈퇴 / 로그인
    INVALID_INPUT(400, "REG001", "입력값이 유효하지 않습니다."),
    DUPLICATED_EMAIL(400, "REG002", "이미 존재하는 email입니다."),
    USER_ALREADY_DELETED(410, "REG003", "이미 탈퇴한 사용자입니다."),
    PASSWORD_NOT_MATCHED(401, "REG004", "비밀번호가 일치하지 않습니다."),
    WITHDRAWN_EMAIL_REUSE_NOT_ALLOWED(409, "REG005", "탈퇴한 계정의 이메일은 다시 사용할 수 없습니다."),
    LOGIN_FAILED(401, "REG001", "이메일 또는 비밀번호가 일치하지 않습니다."),

    // 게시물 좋아요
    LIKE_NOT_FOUND(404, "P-LIK001", "좋아요 기록이 존재하지 않습니다."),
    POST_ALREADY_LIKED(400, "P-LIK002", "이미 좋아요한 게시물입니다."),

    // 인증/인가
    UNAUTHORIZED_USER(401, "AUTH001", "로그인 해주세요."),
    MISMATCH_PASSWORD(400, "AUTH002", "비밀번호가 일치하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
