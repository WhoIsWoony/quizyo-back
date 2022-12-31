package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.user.ReqRegister
import com.whoiswoony.springtutorial.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="유저", description = "유저관련 api 입니다")
@RestController
@RequestMapping("/api/v1")
class UserController(private val userService: UserService) {
    @Operation(summary = "회원 가입", description = "유저를 생성합니다")
    @PostMapping("/register")
    fun register(@RequestBody reqRegister: ReqRegister){
        val result = userService.register(reqRegister)

    }
}