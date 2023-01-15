package com.whoiswoony.springtutorial.controller.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    NOT_EXIST_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 Refresh token 입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    INVALID_EMAIL_FORM(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD_FORM(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다."),
    REGISTER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요.")
}