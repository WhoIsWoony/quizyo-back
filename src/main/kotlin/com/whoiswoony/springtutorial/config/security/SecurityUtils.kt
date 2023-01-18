package com.whoiswoony.springtutorial.config.security
import org.springframework.security.core.context.SecurityContextHolder

fun getMemberEmail(): String {
    val principal = SecurityContextHolder.getContext().authentication?.principal
    //return if (principal is CustomUserDetails) principal else null
    return (principal as CustomUserDetails).username
}