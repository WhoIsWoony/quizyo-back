package com.whoiswoony.springtutorial.dto

import java.time.LocalDateTime

data class User (
    val id: String,
    val email: String,
    val password: String,
    val nickname: LocalDateTime,
    val profile: LocalDateTime
)