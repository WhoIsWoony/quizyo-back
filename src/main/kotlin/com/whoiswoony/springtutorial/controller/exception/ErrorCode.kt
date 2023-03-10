package com.whoiswoony.springtutorial.controller.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    //Auth error code
    NOT_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다."),
    NOT_EXIST_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "존재하지 않는 Refresh token 입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    INVALID_EMAIL_FORM(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD_FORM(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다."),
    REGISTER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    INVALID_AUTHENTICATION_CODE(HttpStatus.BAD_REQUEST, "잘못된 인증번호 입니다."),
    AUTHENTICATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    RESET_PASSWORD_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    CODE_ALREADY_EXPIRED(HttpStatus.BAD_REQUEST, "인증정보의 유효시간이 경과하였습니다. 다시 인증해주세요."),

    //Token error code
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    NOT_ALLOWED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"JWT 토큰이 잘못되었습니다."),

    //Bucket error code
    NOT_FOUND_BUCKET(HttpStatus.BAD_REQUEST,"버킷을 찾을 수 없습니다."),
    INVALID_BUCKET_SHARE(HttpStatus.BAD_REQUEST, "자신이 생성한 버킷은 퍼올 수 없습니다."),
    DUPLICATE_BUCKET_SHARE_MY(HttpStatus.BAD_REQUEST, "이미 퍼온 버킷은 다시 퍼올 수 없습니다."),
    INVALID_IPADDRESS_FORM(HttpStatus.BAD_REQUEST, "잘못된 IP 주소 형식입니다."),
    INVALID_BUCKET_VIEW_UPDATE_TIME(HttpStatus.BAD_REQUEST, "24시간 이내에 동일한 IP로 추가된 조회 수가 존재합니다."),

    //Quiz error code
    NOT_FOUND_QUIZ(HttpStatus.BAD_REQUEST,"퀴즈를 찾을 수 없습니다."),

    //Member error code
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 계정정보 입니다."),
    CHANGE_PASSWORD_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),

    //Reset code error code
    NOT_EXIST_RESET_CODE(HttpStatus.BAD_REQUEST, "존재하지 않는 코드정보 입니다."),
    SAVE_RESET_CODE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
}