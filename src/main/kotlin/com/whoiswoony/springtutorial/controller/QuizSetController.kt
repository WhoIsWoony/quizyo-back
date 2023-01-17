package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.domain.quizset.QuizSet
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import com.whoiswoony.springtutorial.service.quizset.QuizSetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="퀴즈셋", description = "퀴즈셋을 다루는 API 입니다.")
@RestController
@RequestMapping("/user/")
class QuizSetController(private val quizSetService: QuizSetService) {
    @Operation(summary = "퀴즈셋을 추가", description = "퀴즈셋을 추가합니다.")
    @PostMapping("/addQuizSet")
    fun addQuizSet(@RequestBody addQuizSetRequest: AddQuizSetRequest){
        val memberEmail = getMemberEmail()
        quizSetService.addQuizSet(memberEmail, addQuizSetRequest)
    }

    @Operation(summary = "퀴즈셋 전체 불러오기", description = "퀴즈셋을 전부 불러옵니다.")
    @GetMapping("/getQuizSet")
    fun getQuizSet(): MutableList<QuizSet> {
        return quizSetService.getQuizSet()
    }

}