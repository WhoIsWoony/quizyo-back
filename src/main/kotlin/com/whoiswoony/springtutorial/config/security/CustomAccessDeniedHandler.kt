package com.whoiswoony.springtutorial.config.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler:AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        response.status = 403;
        response.characterEncoding = "utf-8";
        response.contentType = "text/html; charset=UTF-8";
        response.writer.write("권한이 없는 사용자입니다.");
    }
}