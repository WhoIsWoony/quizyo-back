package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository:JpaRepository<Member, String>{
    fun findByEmail(email:String):Member?
    fun findByNickname(nickname:String):Member?
    fun findByEmailAndNickname(email: String, nickname: String):Member?
}