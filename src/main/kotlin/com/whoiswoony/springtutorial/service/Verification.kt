package com.whoiswoony.springtutorial.service

import com.whoiswoony.springtutorial.dto.member.VerificationRequest
import com.whoiswoony.springtutorial.service.member.AuthService
import org.springframework.stereotype.Service

@Service
class Verification (
        private val authService: AuthService,
){
    fun typeVerification(verificationRequest: VerificationRequest): String {
        return if(verificationRequest.type=="REGISTER")
            authService.authenticateRegisteringEmail(verificationRequest.email)
        else
            ""
    }
}