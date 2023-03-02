package com.whoiswoony.springtutorial.dto.member

data class IssueResetCodeRequest (
    val memberEmail: String,
    val memberNickname: String,
)