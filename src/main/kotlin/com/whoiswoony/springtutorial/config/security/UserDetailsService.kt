package com.whoiswoony.springtutorial.config.security

import com.whoiswoony.springtutorial.domain.member.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val memberRepository: MemberRepository): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val member = memberRepository.findByEmail(email) ?: throw UsernameNotFoundException("존재하지 않는 이메일 입니다.")
        return CustomUserDetails(member)
    }

}