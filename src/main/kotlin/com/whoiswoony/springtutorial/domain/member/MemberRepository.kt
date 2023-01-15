package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository:JpaRepository<Member, String>{
    fun findByEmail(email:String):Member?
    fun findByNickname(nickname:String):Member?
}