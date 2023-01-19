package com.whoiswoony.springtutorial.dto

data class QuizSetResponse (
    val title:String,
    val description:String,
    val shareNum: Int,
    val viewNum: Int,
    val id:Long?=null
)