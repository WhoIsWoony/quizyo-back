package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.RefreshTokenRequest
import com.whoiswoony.springtutorial.dto.TokenResponse
import com.whoiswoony.springtutorial.logger
import com.whoiswoony.springtutorial.service.member.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Tag(name="유저", description = "유저관련 api 입니다")
@RestController
@RequestMapping("/user/")
class MemberController(private val authService: AuthService) {
    @Operation(summary = "닉네임변경", description = "닉네임 변경 테스트")
    @PostMapping("/changeNickname")
    fun changeNickname(): String {
        return "임시테스트"
    }
}