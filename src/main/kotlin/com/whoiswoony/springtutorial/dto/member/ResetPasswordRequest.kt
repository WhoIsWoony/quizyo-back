package com.whoiswoony.springtutorial.dto.member

data class ResetPasswordRequest (
    val codePassword: String,
    val newPassword: String,
)