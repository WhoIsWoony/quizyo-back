package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.config.security.UserDetailsService
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.*
import com.whoiswoony.springtutorial.dto.member.LoginRequest
import com.whoiswoony.springtutorial.dto.member.RegisterRequest
import com.whoiswoony.springtutorial.service.Validation
import com.whoiswoony.springtutorial.service.member.AuthService
import com.whoiswoony.springtutorial.service.member.SendMail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
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
    val authenticationRepository = mockk<AuthenticationRepository>()
    val resetCodeRepository = mockk<ResetCodeRepository>()
    val jwtUtils : JwtUtils = JwtUtils(UserDetailsService(memberRepository))
    val memberValidation : Validation = Validation()
    val javaMailSender = mockk<JavaMailSender>()
    val resetCode = ResetCode("")
    val memberService : AuthService = AuthService(
        memberRepository = memberRepository,
        refreshTokenRepository = refreshTokenRepository,
        authenticationRepository = authenticationRepository,
        resetCodeRepository = resetCodeRepository,
        passwordEncoder = passwordEncoder(),
        jwtUtils = jwtUtils,
        validation = memberValidation,
        sendMail = SendMail(javaMailSender)
    )

    "이메일 형식 오류"{
        //given
        val wrongEmail = "test"
        val password = "test123!"
        val nickname = "test"
        val registerRequest = RegisterRequest(wrongEmail, password, nickname)

        //when
        val exception = shouldThrow<RuntimeException> { memberService.register(registerRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.INVALID_EMAIL_FORM)
    }

    "비밀번호 형식 오류"{
        //given
        val email = "test@test.com"
        val wrongPassword = "test123"
        val nickname = "test"
        val registerRequest = RegisterRequest(email, wrongPassword, nickname)

        //when
        val exception = shouldThrow<RuntimeException> { memberService.register(registerRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.INVALID_PASSWORD_FORM)
    }

    "이메일 중복체크"{
        //given
        val duplicatedEmail = "test@test.com"
        val password = "test123!"
        val nickname = "test"

        every {
            memberRepository.findByEmail(duplicatedEmail)
        } returns Member(duplicatedEmail, password, nickname, resetCode)

        //when
        val exception = shouldThrow<RuntimeException> { memberService.checkDuplicatedEmail(duplicatedEmail) }


        //then
        exception shouldBe CustomException(ErrorCode.DUPLICATE_EMAIL)
    }

    "닉네임 중복체크"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val duplicatedNickname = "test"

        every {
            memberRepository.findByNickname(duplicatedNickname)
        } returns Member(email, password, duplicatedNickname, resetCode)

        //when
        val response =  shouldThrow<RuntimeException> { memberService.checkDuplicatedNickname(duplicatedNickname) }

        //then
        response shouldBe CustomException(ErrorCode.DUPLICATE_NICKNAME)
    }

    "존재하지 않는 이메일"{
        //given
        val notExistEmail = "test@test.com"
        val password = "test123!"
        val loginRequest = LoginRequest(notExistEmail, password)

        every { memberRepository.findByEmail(notExistEmail) } returns null

        //when
        val exception = shouldThrow<RuntimeException> { memberService.login(loginRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.NOT_EXIST_EMAIL)
    }

    "로그인 시 일치하지 않는 비밀번호"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val wrongPassword = "test123!!"
        val nickname = "test"
        val loginRequest = LoginRequest(email, wrongPassword)

        every {
            memberRepository.findByEmail(email)
        } returns Member(email, password, nickname, resetCode)

        //when
        val exception = shouldThrow<RuntimeException> { memberService.login(loginRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.NOT_EXIST_EMAIL)
    }
})