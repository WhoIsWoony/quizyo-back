package com.whoiswoony.springtutorial.domain.bucket

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BucketShareMyRepository : JpaRepository<BucketShareMy, Long> {
    fun findBucketByMemberId(id: Long): MutableList<BucketShareMy>?
}