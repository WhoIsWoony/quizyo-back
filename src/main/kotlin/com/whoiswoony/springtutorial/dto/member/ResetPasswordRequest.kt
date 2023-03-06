package com.whoiswoony.springtutorial.dto.member

data class ResetPasswordRequest (
    val passwordResetCode: String,
    val newPassword: String,
)