package com.whoiswoony.springtutorial.dto

data class MySharedQuizSetResponse(
    val title:String,
    val description:String,
    val id:Long?=null
)