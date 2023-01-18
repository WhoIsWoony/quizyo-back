package com.whoiswoony.springtutorial.dto

import com.querydsl.core.annotations.QueryProjection

data class QuizSetResponse @QueryProjection constructor(
    val title:String,
    val description:String,
    val views:Long,
    val id:Long,
)