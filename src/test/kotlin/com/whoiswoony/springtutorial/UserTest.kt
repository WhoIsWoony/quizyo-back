package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.service.member.AuthService
import io.kotest.core.spec.style.StringSpec
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserTest:StringSpec ({
    val userRepository = mockk<MemberRepository>()
    val userService = AuthService(userRepository)

    "유저 생성 테스트"{
        //given

        //when

        //then
    }
})