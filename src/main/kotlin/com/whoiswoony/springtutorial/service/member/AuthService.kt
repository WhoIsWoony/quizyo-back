package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.domain.member.Authority
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.dto.LoginRequest
import com.whoiswoony.springtutorial.dto.LoginResponse
import com.whoiswoony.springtutorial.dto.RegisterRequest
import com.whoiswoony.springtutorial.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {

    @Value("\${jwt.access-token-secret}")
    lateinit var accessTokenSecret: String

    fun login(loginRequest: LoginRequest) : LoginResponse {
        val member = memberRepository.findByEmail(loginRequest.email)

        //존재하지 않는 email
        member ?: throw BadCredentialsException("잘못된 계정정보 입니다.")

        //비밀번호 불일치
        if(!passwordEncoder.matches(loginRequest.password, member.password))
            throw BadCredentialsException("잘못된 계정정보 입니다.")

        //token 생성
        val token = jwtUtils.createToken(member.email, member.roles, accessTokenSecret)

        return LoginResponse(token)
    }

    fun register(registerRequest: RegisterRequest){
        //비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(registerRequest.password)

        //Member Entity 생성
        val member = Member(
            registerRequest.email,
            encodedPassword,
            registerRequest.nickname,
        )

        //Member Role = USER로 설정
        member.roles = mutableSetOf(Authority("ROLE_USER", member))

        memberRepository.save(member)
    }
}