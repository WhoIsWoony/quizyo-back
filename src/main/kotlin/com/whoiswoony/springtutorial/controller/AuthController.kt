package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.dto.member.*
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
class AuthController(private val authService: AuthService, private val jwtUtils: JwtUtils) {
    @Operation(summary = "로그인", description = "(email, password) => {accessToken}")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest, response: HttpServletResponse): TokenResponse

    {

        val tokenInfo = authService.login(loginRequest)

        // create a cookie
        val cookie = jwtUtils.createRefreshTokenCookie(tokenInfo.refreshToken)

        // add cookie to response
        response.addCookie(cookie)

        return TokenResponse(tokenInfo.nickname, tokenInfo.accessToken)
    }

    @Operation(summary = "로그아웃", description = "")
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): Boolean {
        val cookies = request.cookies.associate { it.name to it.value }

        authService.logout(cookies["refreshToken"])
        val cookie = jwtUtils.deleteRefreshTokenCookie()

        response.addCookie(cookie)

        return true
    }

    @Operation(summary = "회원가입", description = "(email, password, nickname) =>")
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): Boolean {
        authService.register(registerRequest)
        return true
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
        val cookies = request.cookies?.associate { it.name to it.value }

        cookies?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        tokenResponse = try {
            // refreshToken 추출, request로 변환
            val tokenReissued = authService.refreshToken(cookies["refreshToken"])
            val cookie = jwtUtils.createRefreshTokenCookie(tokenReissued.refreshToken)
            response.addCookie(cookie)
            TokenResponse(tokenReissued.nickname, tokenReissued.accessToken)
        }catch(e:RuntimeException){
            authService.logout(cookies["refreshToken"])
            val cookie = jwtUtils.deleteRefreshTokenCookie()
            response.addCookie(cookie)
            TokenResponse(null,null)
        }

        return tokenResponse
    }

    @Operation(summary = "이메일 중복체크 및 인증번호 발송", description = "(email) => String")
    @PostMapping("/authenticateRegisteringEmail")
    fun authenticateRegisteringEmail(@RequestBody authenticateRegisteringEmailRequest: AuthenticateRegisteringEmailRequest): Boolean {
        return if(!authService.checkDuplicatedEmail(authenticateRegisteringEmailRequest.email))
            authService.authenticateRegisteringEmail(authenticateRegisteringEmailRequest)
        else false
    }

    @Operation(summary = "이메일 인증번호 확인", description = "(email) => Boolean")
    @PostMapping("/checkAuthenticationCode")
    fun checkAuthenticationCode(@RequestBody request: CheckAuthenticationCodeRequest): Boolean {
        return authService.checkAuthenticationCode(request)
    }

    @Operation(summary = "비밀번호 초기화 코드 발급", description = "(email, nickname) => String ")
    @PostMapping("/issueResetCode")
    fun issueResetCode(@RequestBody request: IssueResetCodeRequest): String {
        return authService.issueResetCode(request)
    }

    @Operation(summary = "비밀번호 새로 입력", description = "(resetCode, newPassword) => ")
    @PostMapping("/resetPassword")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) {
        return authService.resetPassword(request)
    }
}