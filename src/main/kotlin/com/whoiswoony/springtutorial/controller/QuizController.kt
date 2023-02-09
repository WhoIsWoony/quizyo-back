package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.dto.quiz.*
import com.whoiswoony.springtutorial.service.quiz.QuizService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name="QUIZ API", description = "단일 퀴즈들의 CRUD를 담당하는 API 입니다.")
@RestController
@RequestMapping("/quiz/")
class QuizController(private val quizService: QuizService) {
    @Operation(summary = "퀴즈추가", description = "(bucketId, title, description) =>")
    @PostMapping("/addQuiz")
    fun addQuiz(@RequestBody addQuizRequest: AddQuizRequest){
        quizService.addQuiz(addQuizRequest)
    }

    @Operation(summary = "퀴즈불러오기", description = "(bucketId) =>")
    @GetMapping("/getQuiz/{bucketId}")
    fun getQuiz(@PathVariable bucketId:Long): GetQuizResponse {
        return quizService.getQuiz(bucketId)
    }

    @Operation(summary = "퀴즈업데이트", description = "() =>")
    @PostMapping("/updateQuiz")
    fun updateQuiz(@RequestBody updateQuizRequest: UpdateQuizRequest) {
        quizService.updateQuiz(updateQuizRequest)
    }

    @Operation(summary = "퀴즈삭제", description = "() =>")
    @PostMapping("/deleteQuiz")
    fun updateQuiz(@RequestBody deleteQuizRequest: DeleteQuizRequest) {
        quizService.deleteQuiz(deleteQuizRequest)
    }

}