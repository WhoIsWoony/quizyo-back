package com.whoiswoony.springtutorial.domain.quizset

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SharedQuizSetRepository : JpaRepository<SharedQuizSet, Long> {
    fun findQuizSetByMemberId(id: Long): MutableList<SharedQuizSet>?
}