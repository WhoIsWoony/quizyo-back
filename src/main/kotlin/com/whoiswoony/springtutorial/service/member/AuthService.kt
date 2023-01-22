package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.*
import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.logger
import com.whoiswoony.springtutorial.service.member.Util.Validation
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val validation: Validation
) {
    fun login(loginRequest: LoginRequest) : Token {
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

        return Token(accessToken, refreshToken)
    }

    fun register(registerRequest: RegisterRequest){
        //이메일 형식 확인
        if(!validation.emailValidation(registerRequest.email))
            throw CustomException(ErrorCode.INVALID_EMAIL_FORM)

        //이메일 db 존재시 true, 아닐시 false 반환
        if(checkDuplicatedEmail(registerRequest.email))
            throw CustomException(ErrorCode.DUPLICATE_EMAIL)

        //비밀번호 형식 확인
        if(!validation.passwordValidation(registerRequest.password))
            throw CustomException(ErrorCode.INVALID_PASSWORD_FORM)
        
        //닉네임 db 존재시 true, 아닐시 false 반환
        if(checkDuplicatedNickname(registerRequest.nickname))
            throw CustomException(ErrorCode.DUPLICATE_NICKNAME)

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
            throw CustomException(ErrorCode.REGISTER_ERROR)
        }
    }

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Token {
        // refresh token db에서 가져오기
        val refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken)
        logger.error(refreshToken?.refreshToken)

        //잘못된 refresh token
        refreshToken ?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        //token 생성
        val newAccessToken = jwtUtils.createAccessToken(refreshToken.member.email, refreshToken.member.roles)

        //refresh token 생성
        val newRefreshToken = jwtUtils.createRefreshToken()

        refreshToken.refreshToken = newRefreshToken
        refreshTokenRepository.save(refreshToken)

        return Token(newAccessToken, newRefreshToken)
    }

    // db에 동일한 이메일 존재시 false 반환
    fun checkDuplicatedEmail(email: String): Boolean {
        return memberRepository.findByEmail(email)!= null
    }

    // db에 동일한 닉네임 존재시 false 반환
    fun checkDuplicatedNickname(nickname: String): Boolean {
        return memberRepository.findByNickname(nickname)!= null
    }
}