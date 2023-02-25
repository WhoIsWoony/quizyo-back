package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.dto.member.*
import com.whoiswoony.springtutorial.service.Verification
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
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["*"])
@Tag(name="AUTH API", description = "유저의 로그인, 회원가입, 중복체크, 토큰관리를 담당하는 API")
@RestController
@RequestMapping("/auth/")
class AuthController(private val authService: AuthService, private val jwtUtils: JwtUtils, private val verification: Verification) {
    @Operation(summary = "로그인", description = "(email, password) => {accessToken}")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest, response: HttpServletResponse): TokenResponse {

        val tokens = authService.login(loginRequest)

        // create a cookie
        val cookie = jwtUtils.createRefreshTokenCookie(tokens.refreshToken)

        // add cookie to response
        response.addCookie(cookie)

        return TokenResponse(tokens.accessToken)
    }

    @Operation(summary = "회원가입", description = "(email, password, nickname) =>")
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest){
        return authService.register(registerRequest)
    }

    @Operation(summary = "중복체크 - 이메일", description = "(email) => boolean")
    @GetMapping("/checkDuplicatedEmail/{email}")
    fun checkDuplicatedEmail(@PathVariable email: String): Boolean {
        return authService.checkDuplicatedEmail(email)
    }

    @Operation(summary = "중복체크 - 닉네임", description = "(nickname) => boolean")
    @GetMapping("/checkDuplicatedNickname/{nickname}")
    fun checkDuplicatedNickname(@PathVariable nickname: String): Boolean {
        return authService.checkDuplicatedNickname(nickname)
    }

    @Operation(summary = "토큰재발급", description = "() => accessToken")
    @PostMapping("/refreshToken")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): TokenResponse {
        lateinit var tokenResponse: TokenResponse
        try {
            // refreshToken 추출, request로 변환
            val cookies = request.cookies.associate { it.name to it.value }
            val tokenReissued = authService.refreshToken(cookies["refreshToken"])
            val cookie = jwtUtils.createRefreshTokenCookie(tokenReissued.refreshToken)
            response.addCookie(cookie)
            tokenResponse = TokenResponse(tokenReissued.accessToken)
        }catch(e:RuntimeException){
            val cookie = jwtUtils.deleteRefreshTokenCookie()
            response.addCookie(cookie)
            tokenResponse = TokenResponse("")
        }
        return tokenResponse
    }

    @Operation(summary = "인증번호 발송", description = "(email) => String")
    @PostMapping("/verifyRegisteringEmail")
    fun verifyRegisteringEmail(@RequestParam email: String): String {
        return verification.typeVerification(VerificationRequest(email, "REGISTER"))
    }
}