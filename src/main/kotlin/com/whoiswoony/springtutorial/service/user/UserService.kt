package com.whoiswoony.springtutorial.service.user

import com.whoiswoony.springtutorial.domain.user.User
import com.whoiswoony.springtutorial.domain.user.UserRepository
import com.whoiswoony.springtutorial.dto.user.ReqRegister
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun register(reqRegister: ReqRegister){
        val user = reqRegister.toUser()
        userRepository.save(user)
    }
}