package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.dto.member.ChangePasswordRequest
import com.whoiswoony.springtutorial.service.Validation
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val validation: Validation,
){
    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        val member = memberRepository.findByEmail(changePasswordRequest.memberEmail)

        // member 존재x 시 오류
        member ?: throw CustomException(ErrorCode.NOT_EXIST_MEMBER)

        //입력된 기존 비밀번호와 db에 저장된 비밀번호 비교
        if(passwordEncoder.matches(changePasswordRequest.oldPassword, member.password)){
            val newEncodedPassword = passwordEncoder.encode(changePasswordRequest.newPassword)

            //비밀번호 형식 확인
            if(!validation.passwordValidation(changePasswordRequest.newPassword))
                throw CustomException(ErrorCode.INVALID_PASSWORD_FORM)

            member.password = newEncodedPassword

            try{
                memberRepository.save(member)
            }catch (e:Exception){
                throw CustomException(ErrorCode.CHANGE_PASSWORD_ERROR)
            }
        }
        else throw CustomException(ErrorCode.INVALID_PASSWORD)
    }
}