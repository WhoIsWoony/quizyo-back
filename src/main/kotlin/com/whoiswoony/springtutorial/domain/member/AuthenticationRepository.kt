package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface AuthenticationRepository:JpaRepository<Authentication, String>{
    fun findByEmailAndCode(email: String, code: String):Authentication?
    @Transactional
    fun deleteByEmail(email: String)
}