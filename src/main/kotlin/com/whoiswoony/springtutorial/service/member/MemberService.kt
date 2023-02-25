package com.whoiswoony.springtutorial.service.member

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.dto.member.IssueTemporalPasswordRequest
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val sendMail: SendMail
){
    fun issueTemporalPassword(resetPasswordRequest: IssueTemporalPasswordRequest): String {
        val member = memberRepository.findByEmailAndNickname(resetPasswordRequest.email, resetPasswordRequest.nickname)

        // 존재하지 않는 member
        member ?: throw CustomException(ErrorCode.NOT_EXIST_MEMBER)

        // 임시 비밀번호 생성
        val randomPassword = sendMail.randomNumberGenerator(12)
        sendMail.SendMailForm(
                from = "noreply@quizyo.com",
                to = member.email,
                title = "quizyo 임시 비밀번호 발급",
                content = member.nickname + "님의 임시 비밀번호는 " + randomPassword + "입니다."
        )

        //비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(randomPassword)

        member.password = encodedPassword
        memberRepository.save(member)

        return "성공적으로 메일을 발송하였습니다."
    }
}