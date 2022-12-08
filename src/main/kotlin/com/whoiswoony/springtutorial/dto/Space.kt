package com.whoiswoony.springtutorial.dto

data class Space(
    val id: String,
    val title:String,
    val content:String,
    val private:Boolean,
    var sets: MutableList<Set> = ArrayList(),
)