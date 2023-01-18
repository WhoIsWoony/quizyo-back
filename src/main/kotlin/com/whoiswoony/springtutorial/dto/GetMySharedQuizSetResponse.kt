package com.whoiswoony.springtutorial.dto

data class GetMySharedQuizSetResponse(
    val title:String,
    val description:String,
    val id:Long? = null
)