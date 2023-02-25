package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.*
import com.whoiswoony.springtutorial.dto.member.*
import com.whoiswoony.springtutorial.logger
import com.whoiswoony.springtutorial.service.Validation
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val validation: Validation,
    private val sendMail: SendMail
) {
    fun login(loginRequest: LoginRequest) : TokenInfo {
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

        return TokenInfo(member.nickname, accessToken, refreshToken)
    }

    fun logout(refreshToken: String?) {
        //refreshToken이 null일 시
        refreshToken ?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        val refreshTokenRequest = RefreshTokenRequest(refreshToken)

        // refresh token db에서 가져오기
        val refreshTokenFound = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken)

        //refresh token이 db에 존재하지 않을 시
        refreshTokenFound ?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        //token 삭제
        refreshTokenRepository.delete(refreshTokenFound)
    }

    fun register(registerRequest: RegisterRequest){
        //이메일 형식 확인
        if(!validation.emailValidation(registerRequest.email))
            throw CustomException(ErrorCode.INVALID_EMAIL_FORM)

        //비밀번호 형식 확인
        if(!validation.passwordValidation(registerRequest.password))
            throw CustomException(ErrorCode.INVALID_PASSWORD_FORM)

        //이메일 db 존재시 true, 아닐시 false 반환
        if(checkDuplicatedEmail(registerRequest.email))
            throw CustomException(ErrorCode.DUPLICATE_EMAIL)
        
        //닉네임 db 존재시 true, 아닐시 false 반환
        if(checkDuplicatedNickname(registerRequest.nickname))
            throw CustomException(ErrorCode.DUPLICATE_NICKNAME)

        val authentication = authenticationRepository.findByEmailAndCodeAndType(registerRequest.email, registerRequest.code, "REGISTER")

        authentication ?: throw CustomException(ErrorCode.INVALID_VERIFICATION_CODE)

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
            authenticationRepository.delete(authentication)
        }catch (e:Exception){
            println(e)
            throw CustomException(ErrorCode.REGISTER_ERROR)
        }
    }

    fun authenticateRegisteringEmail(email: String): String{
        if(checkDuplicatedEmail(email))
            throw CustomException(ErrorCode.DUPLICATE_EMAIL)
        else
        {
            //기존 인증 코드 존재시 삭제
            authenticationRepository.deleteByEmailAndType(email, "REGISTER")
            //랜덤 인증코드 생성
            val randomCode = sendMail.randomNumberGenerator(12)

            //Authentication Entity 생성
            var authentication = Authentication(
                email,
                randomCode,
                "REGISTER"
            )
            authenticationRepository.save(authentication)

            sendMail.SendMailForm(
                    from = "noreply@quizyo.com",
                    to = email,
                    title = "quizyo 임시 비밀번호 발급",
                    content = "회원님의 이메일 인증번호는 " + randomCode + "입니다."
            )
            return "성공적으로 메일을 발송하였습니다."
        }
    }

    fun refreshToken(refreshToken: String?): TokenInfo {
        //refreshToken이 null일 시
        refreshToken ?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        val refreshTokenRequest = RefreshTokenRequest(refreshToken)

        // refresh token db에서 가져오기
        val refreshTokenFound = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken)
        logger.error(refreshTokenFound?.refreshToken)

        //refresh token이 db에 존재하지 않을 시
        refreshTokenFound ?: throw CustomException(ErrorCode.NOT_EXIST_REFRESH_TOKEN)

        //token 생성
        val newAccessToken = jwtUtils.createAccessToken(refreshTokenFound.member.email, refreshTokenFound.member.roles)

        //refresh token 생성
        val newRefreshToken = jwtUtils.createRefreshToken()

        refreshTokenFound.refreshToken = newRefreshToken
        refreshTokenRepository.save(refreshTokenFound)

        return TokenInfo(refreshTokenFound.member.nickname, newAccessToken, newRefreshToken)
    }

    // db에 동일한 이메일 존재시 true 반환
    fun checkDuplicatedEmail(email: String): Boolean {
        return memberRepository.findByEmail(email)!= null
    }

    // db에 동일한 닉네임 존재시 true 반환
    fun checkDuplicatedNickname(nickname: String): Boolean {
        return memberRepository.findByNickname(nickname)!= null
    }
}