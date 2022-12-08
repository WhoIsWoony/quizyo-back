package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "QuizS : Auth API")
@RestController
@RequestMapping("/")
class Auth(private val todoService: TodoService) {

    @Operation(summary = "로그인")
    @GetMapping("/signIn")
    fun signIn(
        @RequestParam email:String,
        @RequestParam password:String
    ): Boolean {
        return true
    }

    @Operation(summary = "회원가입")
    @GetMapping("/signUp")
    fun signUp(
        @RequestParam email:String,
        @RequestParam password:String,
        @RequestParam nickname:String,
        @RequestParam profile:String?,
    ): Boolean {
        return true
    }

    @Operation(summary = "이메일인증")
    @GetMapping("/verifyEmail")
    fun verifyEmail(
        @RequestParam encodedEmail:String,
        @RequestParam secretCode:String
    ): Boolean {
        return true
    }

    @Operation(summary = "비밀번호변경요청")
    @GetMapping("/requestChangePassword")
    fun requestChangePassword(
        @RequestParam email:String
    ): Boolean {
        return true
    }

    @Operation(summary = "비밀번호변경")
    @GetMapping("/changePassword")
    fun changePassword(
        @RequestParam encodedEmail:String,
        @RequestParam secretCode:String,
        @RequestParam newPassword:String
    ): Boolean {
        return true
    }
}
