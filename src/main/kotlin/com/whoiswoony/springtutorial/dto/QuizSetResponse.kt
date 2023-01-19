package com.whoiswoony.springtutorial.dto

import com.querydsl.core.annotations.QueryProjection

data class QuizSetResponse @QueryProjection constructor(
    val title:String,
    val description:String,
    val shareNum: Long,
    val viewNum: Long,
    val id:Long?=null,
)