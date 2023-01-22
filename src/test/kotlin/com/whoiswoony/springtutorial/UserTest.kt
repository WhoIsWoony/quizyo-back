package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.member.RefreshTokenRepository
import com.whoiswoony.springtutorial.service.member.AuthService
import com.whoiswoony.springtutorial.service.member.Util.Validation
import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.config.security.UserDetailsService
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.dto.LoginRequest
import com.whoiswoony.springtutorial.dto.RegisterRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Bean
fun passwordEncoder(): PasswordEncoder {
    return NoOpPasswordEncoder.getInstance();
}

@ExtendWith(MockKExtension::class)
class UserTest:StringSpec ({
    val memberRepository = mockk<MemberRepository>()
    val refreshTokenRepository = mockk<RefreshTokenRepository>()
    val jwtUtils : JwtUtils = JwtUtils(UserDetailsService(memberRepository))
    val userValidation : Validation = Validation()
    val userService : AuthService = AuthService(
        memberRepository = memberRepository,
        refreshTokenRepository = refreshTokenRepository,
        passwordEncoder = passwordEncoder(),
        jwtUtils = jwtUtils,
        validation = userValidation
    )

    "이메일 형식 오류"{
        //given
        val wrongEmail = "test"
        val password = "test123!"
        val nickname = "test"
        val registerRequest = RegisterRequest(wrongEmail, password, nickname)

        //when
        val exception = shouldThrow<RuntimeException> {
            userService.register(registerRequest)
        }

        //then
        exception shouldBe CustomException(ErrorCode.INVALID_EMAIL_FORM)
    }

    "존재하지 않는 이메일"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val loginRequest = LoginRequest(email, password)

        every { memberRepository.findByEmail(email) } returns Member(email, password, "test")

        //when
        val exception = shouldThrow<RuntimeException> { userService.login(loginRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.NOT_EXIST_EMAIL)
    }
})