package com.whoiswoony.springtutorial.dto.quiz

data class AddQuizRequest (
    val bucketId: Long,
    val order: Long,
    val question: String,
    val answer: String,
)