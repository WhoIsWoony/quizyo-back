package com.whoiswoony.springtutorial.dto.quiz

data class UpdateQuizRequest (
    val quizId: Long,
    val sequence: Long,
    val question: String,
    val answer: String,
)