package com.whoiswoony.springtutorial.dto

import javax.persistence.*

data class Quiz (
    val id: String,
    val question:String,
    val answer:String
)