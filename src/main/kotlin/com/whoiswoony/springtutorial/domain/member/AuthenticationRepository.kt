package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface AuthenticationRepository:JpaRepository<Authentication, String>{
    fun findByEmailAndCodeAndType(email: String, code: String, type: String):Authentication?
    @Transactional
    fun deleteByEmailAndType(email: String, type: String)
}