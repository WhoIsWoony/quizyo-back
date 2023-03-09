package com.whoiswoony.springtutorial.config.security

import com.whoiswoony.springtutorial.logger
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint:AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.status = 401;
        response.characterEncoding = "utf-8";
        response.contentType = "text/html; charset=UTF-8";
        response.writer.write("인증되지 않은 사용자 입니다.");
    }
}