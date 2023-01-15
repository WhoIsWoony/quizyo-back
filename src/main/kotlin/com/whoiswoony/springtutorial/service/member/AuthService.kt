package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.*
import com.whoiswoony.springtutorial.dto.LoginRequest
import com.whoiswoony.springtutorial.dto.RefreshTokenRequest
import com.whoiswoony.springtutorial.dto.TokenResponse
import com.whoiswoony.springtutorial.dto.RegisterRequest
import com.whoiswoony.springtutorial.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) {
    fun login(loginRequest: LoginRequest) : TokenResponse {
        val member = memberRepository.findByEmail(loginRequest.email)

        //존재하지 않는 email
        member ?: throw CustomException(ErrorCode.NOT_EXIST_EMAIL)

        //비밀번호 불일치
        if(!passwordEncoder.matches(loginRequest.password, member.password))
            throw CustomException(ErrorCode.INVALID_PASSWORD)

        //token 생성
        val accessToken = jwtUtils.createAccessToken(member.email, member.roles)

        //refresh token 생성
        val refreshToken = jwtUtils.createRefreshToken()

        val refreshTokenEntity = RefreshToken(
                member,
                refreshToken
        )

        refreshTokenRepository.save(refreshTokenEntity)

        return TokenResponse(accessToken, refreshToken)
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

        try{
            memberRepository.save(member)
        }catch (e:Exception){
            throw CustomException(ErrorCode.DUPLICATE_EMAIL)
        }
    }

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        // refresh token db에서 가져오기
        val refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken)

        refreshToken?: throw IllegalArgumentException("Refresh Token이 존재하지 않습니다.")

        val member = refreshToken.member

        //token 생성
        val newAccessToken = jwtUtils.createAccessToken(member.email, member.roles)

        //refresh token 생성
        val newRefreshToken = jwtUtils.createRefreshToken()

        val refreshTokenEntity = RefreshToken(
                member,
                newRefreshToken
        )

        refreshTokenRepository.delete(refreshToken)
        refreshTokenRepository.save(refreshTokenEntity)
        return TokenResponse(newAccessToken, newRefreshToken)
    }
}