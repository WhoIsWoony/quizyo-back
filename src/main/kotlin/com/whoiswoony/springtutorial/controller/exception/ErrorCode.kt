package com.whoiswoony.springtutorial.controller.exception

enum class ErrorCode(val status: Int, val message: String) {
    //Auth error code
    NOT_EXIST_EMAIL(1001, "잘못된 계정정보 입니다."),
    INVALID_PASSWORD(1001, "잘못된 계정정보 입니다."),
    NOT_EXIST_REFRESH_TOKEN(1002, "존재하지 않는 Refresh token 입니다."),
    DUPLICATE_EMAIL(1003, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(1004, "이미 존재하는 닉네임입니다."),
    INVALID_EMAIL_FORM(1005, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD_FORM(1006, "잘못된 비밀번호 형식입니다."),
    REGISTER_ERROR(1007, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    INVALID_AUTHENTICATION_CODE(1008, "잘못된 인증번호 입니다."),
    AUTHENTICATION_ERROR(1009,"요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    RESET_PASSWORD_ERROR(1010, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
    CODE_ALREADY_EXPIRED(1011, "인증정보의 유효시간이 경과하였습니다. 다시 인증해주세요."),

    //Token error code
    INVALID_TOKEN_SIGNATURE(2001, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(2002, "만료된 JWT 토큰입니다."),
    NOT_ALLOWED_TOKEN(2003, "지원되지 않는 JWT 토큰입니다."),
    INVALID_TOKEN(2004,"JWT 토큰이 잘못되었습니다."),

    //Bucket error code
    NOT_FOUND_BUCKET(3001,"버킷을 찾을 수 없습니다."),
    INVALID_BUCKET_SHARE(3002, "자신이 생성한 버킷은 퍼올 수 없습니다."),
    DUPLICATE_BUCKET_SHARE_MY(3003, "이미 퍼온 버킷은 다시 퍼올 수 없습니다."),
    INVALID_IPADDRESS_FORM(3004, "잘못된 IP 주소 형식입니다."),
    INVALID_BUCKET_VIEW_UPDATE_TIME(3005, "24시간 이내에 동일한 이메일로 추가된 조회 수가 존재합니다."),

    //Quiz error code
    NOT_FOUND_QUIZ(4001,"퀴즈를 찾을 수 없습니다."),

    //Member error code
    NOT_EXIST_MEMBER(5001, "존재하지 않는 계정정보 입니다."),
    CHANGE_PASSWORD_ERROR(5002, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),

    //Reset code error code
    NOT_EXIST_RESET_CODE(6001, "존재하지 않는 코드정보 입니다."),
    SAVE_RESET_CODE_ERROR(6002, "요청된 작업을 처리할 수 없습니다. 다시 시도해주세요."),
}