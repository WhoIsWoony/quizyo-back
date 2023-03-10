package com.whoiswoony.springtutorial.service.member

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

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

    //영어 대소문자 + 숫자
    fun randomCodeGenerator(codeLength: Int): String {
        return RandomStringUtils.randomAlphanumeric(codeLength)
    }

    //영어 대소문자 + 숫자 + 1개의 특수문자
    fun randomCodeGeneratorWithSpecialCharacter(codeLength: Int): String{

        val specialCharacterSet = "!@#$%^&*"

        var randomCode = RandomStringUtils.randomAlphanumeric(codeLength - 1)

        val randomCodeIndex = (Math.random() * codeLength).toInt()
        val randomSpecialCharacterIndex = (Math.random() * specialCharacterSet.length).toInt()

        return randomCode.substring(0, randomCodeIndex) + specialCharacterSet[randomSpecialCharacterIndex] + randomCode.substring(randomCodeIndex)
    }

    //숫자
    fun randomAuthenticationCodeGenerator(codeLength: Int): String {
        return RandomStringUtils.randomNumeric(codeLength)
    }
}