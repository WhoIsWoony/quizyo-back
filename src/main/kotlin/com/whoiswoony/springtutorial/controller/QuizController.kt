package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.dto.quiz.AddQuizRequest
import com.whoiswoony.springtutorial.service.quiz.QuizService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="QUIZ API", description = "단일 퀴즈들의 CRUD를 담당하는 API 입니다.")
@RestController
@RequestMapping("/quiz/")
class QuizController(private val quizService: QuizService) {
    @Operation(summary = "퀴즈추가", description = "(title, description) =>")
    @PostMapping("/addQuiz")
    fun addBucket(@RequestBody addQuizRequest: AddQuizRequest){
        quizService.addQuiz(addQuizRequest)
    }

}