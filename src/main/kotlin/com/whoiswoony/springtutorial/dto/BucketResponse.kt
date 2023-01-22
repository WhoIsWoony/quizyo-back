package com.whoiswoony.springtutorial.dto

import com.querydsl.core.annotations.QueryProjection

data class BucketResponse @QueryProjection constructor(
    val title:String,
    val description:String,
    val shareNum: Int,
    val viewNum: Int,
    val id:Long?=null,
)