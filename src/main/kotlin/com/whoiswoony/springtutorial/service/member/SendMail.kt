package com.whoiswoony.springtutorial.service.member

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class SendMail (
    private val javaMailSender: JavaMailSender
){
    fun SendMailForm(
        from:String,
        to:String,
        title:String,
        content:String
    ){
       val email = SimpleMailMessage()
       email.setFrom(from)
       email.setTo(to)
       email.setSubject(title)
       email.setText(content)
       javaMailSender.send(email)
    }

    // 무작위 문자열 생성 함수
    fun randomCodeGenerator(codeLength: Int): String{

        val specialCharacterSet = "!@#$%^&*"

        var randomCode = RandomStringUtils.randomAlphanumeric(codeLength - 1)

        val randomCodeIndex = (Math.random() * codeLength).toInt()
        val randomSpecialCharacterIndex = (Math.random() * specialCharacterSet.length).toInt()

        return randomCode.substring(0, randomCodeIndex) + specialCharacterSet[randomSpecialCharacterIndex] + randomCode.substring(randomCodeIndex)
    }
}