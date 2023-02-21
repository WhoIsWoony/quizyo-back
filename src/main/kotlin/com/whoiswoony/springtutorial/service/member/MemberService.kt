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
    private val javaMailSender: JavaMailSender
){
    fun issueTemporalPassword(resetPasswordRequest: IssueTemporalPasswordRequest): String {
        val member = memberRepository.findByEmailAndNickname(resetPasswordRequest.email, resetPasswordRequest.nickname)

        // 존재하지 않는 member
        member ?: throw CustomException(ErrorCode.NOT_EXIST_MEMBER)

        // 임시 비밀번호 생성
        val randomPassword = randomPasswordGenerator(12)

        val email = SimpleMailMessage()
        email.setFrom("noreply@quizyo.com")
        email.setTo(member.email)
        email.setSubject("quizyo 임시 비밀번호 발급")
        email.setText(member.nickname + " 님의 임시 비밀번호는 " + randomPassword + " 입니다.")
        javaMailSender.send(email)

        //비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(randomPassword)

        member.password = encodedPassword
        memberRepository.save(member)

        return "성공적으로 메일을 발송하였습니다."
    }

    // 무작위 비밀번호 생성 함수
    // 비밀번호는 6 ~ passwordLength의 길이를 가짐
    fun randomPasswordGenerator(passwordLength:Int):String {
        val charPool: List<Char> = ('a'..'z')+('A'..'Z')+('0'..'9')

        return (6..passwordLength)
                .map { kotlin.random.Random.nextInt(0,charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }
}