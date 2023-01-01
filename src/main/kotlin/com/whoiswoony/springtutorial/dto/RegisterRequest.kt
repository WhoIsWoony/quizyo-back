package com.whoiswoony.springtutorial.dto

data class RegisterRequest(
    val email:String,
    val password:String,
    val nickname:String,
)