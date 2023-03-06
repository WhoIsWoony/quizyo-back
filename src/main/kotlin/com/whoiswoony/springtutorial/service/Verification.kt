package com.whoiswoony.springtutorial.service

import com.whoiswoony.springtutorial.dto.member.AuthenticationRequest
import com.whoiswoony.springtutorial.service.member.AuthService
import org.springframework.stereotype.Service

@Service
class Verification (
        private val authService: AuthService,
){
    fun typeVerification(authenticationRequest: AuthenticationRequest): Boolean {
        return if(authenticationRequest.type=="REGISTER")
            authService.authenticateRegisteringEmail(authenticationRequest.email)
        else
            false
    }
}