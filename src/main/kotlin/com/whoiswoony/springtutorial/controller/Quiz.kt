package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@Tag(name = "QuizS : Quiz API")
@RestController
@RequestMapping("/")
class Quiz(private val todoService: TodoService) {

    @Operation(summary = "그룹내퀴즈들불러오기")
    @GetMapping("/findQuizBySetId")
    fun findQuizBySetId(
        @RequestParam accessToken:String,
        @RequestParam setId:BigInteger,
    ): Boolean {
        return true
    }
}
