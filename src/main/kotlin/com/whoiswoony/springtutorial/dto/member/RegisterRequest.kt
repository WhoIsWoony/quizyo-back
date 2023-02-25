package com.whoiswoony.springtutorial.dto.member

data class RegisterRequest(
    val email:String,
    val password:String,
    val nickname:String,
    val code:String,
)