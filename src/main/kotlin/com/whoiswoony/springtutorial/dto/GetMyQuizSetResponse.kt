package com.whoiswoony.springtutorial.dto

import com.whoiswoony.springtutorial.domain.quizset.SharedQuizSet

data class GetMyQuizSetResponse (
    val myOwnQuizSetResponse: MutableList<MyOwnQuizSetResponse>? = mutableListOf(),
    val mySharedQuizSetResponse: MutableList<MySharedQuizSetResponse>? = mutableListOf()
)