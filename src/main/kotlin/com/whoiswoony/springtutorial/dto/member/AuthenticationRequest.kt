package com.whoiswoony.springtutorial.dto.member

data class AuthenticationRequest
(
    val email: String,
    val type: String,
)