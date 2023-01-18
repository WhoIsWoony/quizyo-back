package com.whoiswoony.springtutorial.dto

data class MyOwnQuizSetResponse (
    val title:String,
    val description:String,
    val sharedQuizSetsCount: Int,
    val id:Long?=null
)