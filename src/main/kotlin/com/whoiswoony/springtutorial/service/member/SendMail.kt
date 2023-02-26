package com.whoiswoony.springtutorial.service.member

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

    // 무작위 번호 생성 함수
    fun randomNumberGenerator(codeLength:Int):String {
        val charPool: List<Char> = ('a'..'z')+('!'..'+')+('A'..'Z')+('0'..'9')

        return (6..codeLength)
            .map { kotlin.random.Random.nextInt(0,charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}