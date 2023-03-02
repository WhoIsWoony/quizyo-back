package com.whoiswoony.springtutorial.dto.member

data class ResetPasswordRequest (
    val memberEmail: String,
    val passwordResetCode: String,
    val newPassword: String,
)