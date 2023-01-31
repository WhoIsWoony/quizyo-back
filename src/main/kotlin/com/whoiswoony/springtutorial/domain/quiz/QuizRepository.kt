package com.whoiswoony.springtutorial.domain.quiz

import com.whoiswoony.springtutorial.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository:JpaRepository<Quiz, Long>{
    fun findByBucketId(bucketId:Long): MutableList<Quiz>
}