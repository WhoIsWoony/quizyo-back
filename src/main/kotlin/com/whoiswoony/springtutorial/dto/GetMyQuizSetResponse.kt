package com.whoiswoony.springtutorial.dto

import com.whoiswoony.springtutorial.domain.quizset.SharedQuizSet

data class GetMyQuizSetResponse (
    val myOwnQuizSetResponse: MutableList<QuizSetResponse>? = mutableListOf(),
    val mySharedQuizSetResponse: MutableList<QuizSetResponse>? = mutableListOf()
)