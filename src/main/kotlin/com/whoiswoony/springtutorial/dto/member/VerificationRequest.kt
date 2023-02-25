package com.whoiswoony.springtutorial.dto.member

data class VerificationRequest
(
    val email: String,
    val type: String,
)