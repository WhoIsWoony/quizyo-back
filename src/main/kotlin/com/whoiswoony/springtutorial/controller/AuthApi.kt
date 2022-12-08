package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "QuizS : Auth API")
@RestController
@RequestMapping("/")
class AuthApi(private val todoService: TodoService) {

    @Operation(summary = "로그인")
    @PostMapping("/signIn")
    fun signIn(
        @RequestParam email:String,
        @RequestParam password:String
    ): Boolean {
        return true
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    fun signUp(
        @RequestParam email:String,
        @RequestParam password:String,
        @RequestParam nickname:String,
        @RequestParam profile:String?,
    ): Boolean {
        return true
    }

    @Operation(summary = "이메일인증")
    @PostMapping("/verifyEmail")
    fun verifyEmail(
        @RequestParam encodedEmail:String,
        @RequestParam secretCode:String
    ): Boolean {
        return true
    }

    @Operation(summary = "비밀번호변경요청")
    @PostMapping("/requestChangePassword")
    fun requestChangePassword(
        @RequestParam email:String
    ): Boolean {
        return true
    }

    @Operation(summary = "비밀번호변경")
    @PostMapping("/changePassword")
    fun changePassword(
        @RequestParam encodedEmail:String,
        @RequestParam secretCode:String,
        @RequestParam newPassword:String
    ): Boolean {
        return true
    }
}
