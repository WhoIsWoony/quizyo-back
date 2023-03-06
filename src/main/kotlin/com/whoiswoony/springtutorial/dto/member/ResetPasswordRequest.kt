package com.whoiswoony.springtutorial.dto.member

data class ResetPasswordRequest (
    val code: String,
    val password: String,
)