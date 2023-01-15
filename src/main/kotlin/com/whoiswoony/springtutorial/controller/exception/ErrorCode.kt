package com.whoiswoony.springtutorial.controller.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
}