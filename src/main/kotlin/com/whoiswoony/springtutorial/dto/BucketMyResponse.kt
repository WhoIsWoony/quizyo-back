package com.whoiswoony.springtutorial.dto

data class BucketMyResponse (
    val bucketMy: MutableList<BucketResponse>? = mutableListOf(),
    val bucketShareMy: MutableList<BucketResponse>? = mutableListOf()
)