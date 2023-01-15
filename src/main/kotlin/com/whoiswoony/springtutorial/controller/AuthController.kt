package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.service.member.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.Email

@CrossOrigin(origins = ["*"])
@Tag(name="유저", description = "유저관련 api 입니다")
@RestController
@RequestMapping("/auth/")
class AuthController(private val authService: AuthService) {
    @Operation(summary = "로그인", description = "로그인입니다.")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest, response: HttpServletResponse): TokenResponse {

        val tokens = authService.login(loginRequest)

        // create a cookie
        val cookie = Cookie("refreshToken", tokens.refreshToken)

        // expires in 1 day
        cookie.maxAge = 1 * 24 * 60 * 60

        // optional properties
        cookie.secure = false
        cookie.isHttpOnly = false
        cookie.path = "/"

        // add cookie to response
        response.addCookie(cookie)

        return TokenResponse(tokens.accessToken)
    }

    @Operation(summary = "회원가입", description = "유저를 생성합니다")
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest){
        return authService.register(registerRequest)
    }

    @Operation(summary = "refresh token 재발급", description = "refresh token을 재발급합니다.")
    @PostMapping("/refreshToken")
    fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): Token {
        return authService.refreshToken(refreshTokenRequest)
    }

    @Operation(summary = "email 중복 체크", description = "email 중복을 체크합니다.")
    @GetMapping("/checkDuplicatedEmail/{email}")
    fun checkDuplicatedEmail(@PathVariable email: String): Boolean {
        return authService.checkDuplicatedEmail(email)
    }

    @Operation(summary = "nickname 중복 체크", description = "nickname 중복을 체크합니다.")
    @GetMapping("/checkDuplicatedNickname/{nickname}")
    fun checkDuplicatedNickname(@PathVariable nickname: String): Boolean {
        return authService.checkDuplicatedNickname(nickname)
    }
}