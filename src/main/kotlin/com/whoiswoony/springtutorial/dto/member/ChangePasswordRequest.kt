package com.whoiswoony.springtutorial.dto.member

data class ChangePasswordRequest (
    val memberEmail: String,
    val oldPassword: String,
    val newPassword: String,
)