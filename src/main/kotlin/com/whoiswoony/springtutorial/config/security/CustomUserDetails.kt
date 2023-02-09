package com.whoiswoony.springtutorial.config.security

import com.whoiswoony.springtutorial.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class CustomUserDetails(private val member: Member):UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
        = member.roles.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())

    override fun getUsername(): String = member.email

    override fun getPassword(): String = member.password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}