package com.whoiswoony.springtutorial.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val jwtUtils: JwtUtils): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // request로 부터 token 추출
        val token = jwtUtils.resolveToken(request)

        // token 검증시 유효할 시 : 관리토큰 생성 후 Spring Security에게 위임
        if (token != null && jwtUtils.isValid(token)) {
            // JWT로 AuthenticationToken 생성
            val authentication: Authentication = jwtUtils.getAuthentication(token)
            // 생성된 AuthenticationToken을 SecurityContext가 관리하도록 설정
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}