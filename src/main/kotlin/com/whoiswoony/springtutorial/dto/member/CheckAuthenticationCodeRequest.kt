package com.whoiswoony.springtutorial.dto.member

data class CheckAuthenticationCodeRequest
    (
    val email: String,
    val code: String,
    val type: String,
)