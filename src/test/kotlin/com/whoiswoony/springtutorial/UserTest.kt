package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.domain.user.UserRepository
import com.whoiswoony.springtutorial.dto.user.ReqRegister
import com.whoiswoony.springtutorial.service.user.UserService
import io.kotest.core.spec.style.StringSpec
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserTest:StringSpec ({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    "유저 생성 테스트"{
        //given
        val reqRegister = ReqRegister(uid = "test_id_1", upw = "dkaghghkehla1")

        //when
        userService.register(reqRegister)

        //then
    }
})