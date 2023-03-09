package com.whoiswoony.springtutorial.dto.member

data class IssueResetCodeRequest (
    val email: String,
    val nickname: String,
)