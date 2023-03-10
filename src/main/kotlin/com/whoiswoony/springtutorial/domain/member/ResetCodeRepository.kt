package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResetCodeRepository:JpaRepository<ResetCode, Long> {
    fun findByCode(code: String):ResetCode?
}