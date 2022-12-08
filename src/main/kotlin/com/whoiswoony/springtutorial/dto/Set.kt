package com.whoiswoony.springtutorial.dto

data class Set (
    val id: String,
    val title:String,
    var quizs: MutableList<Quiz> = ArrayList(),
)