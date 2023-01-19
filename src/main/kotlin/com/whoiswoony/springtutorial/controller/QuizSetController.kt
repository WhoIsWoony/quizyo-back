package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import com.whoiswoony.springtutorial.dto.QuizSetResponse
import com.whoiswoony.springtutorial.dto.AddSharedQuizSetRequest
import com.whoiswoony.springtutorial.service.quizset.QuizSetService
import com.whoiswoony.springtutorial.service.quizset.SharedQuizSetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name="퀴즈셋", description = "퀴즈셋을 다루는 API 입니다.")
@RestController
@RequestMapping("/quizSet/")
class QuizSetController(private val quizSetService: QuizSetService, private val sharedQuizSetService: SharedQuizSetService) {

    @Operation(summary = "퀴즈셋을 추가", description = "퀴즈셋을 추가합니다.")
    @PostMapping("/addQuizSet")
    fun addQuizSet(@RequestBody addQuizSetRequest: AddQuizSetRequest){
        val memberEmail = getMemberEmail()
        quizSetService.addQuizSet(memberEmail, addQuizSetRequest)
    }

    @Operation(summary = "퀴즈셋 전체 불러오기", description = "퀴즈셋을 전부 불러옵니다.")
    @GetMapping("/getQuizSet")
    fun getQuizSet(): MutableList<QuizSetResponse> {
        return quizSetService.getQuizSet()
    }

    @Operation(summary = "퀴즈셋 조회수 추가", description = "퀴즈셋의 조회수를 추가합니다.")
    @GetMapping("/addQuizSetView/{quizSetId}/{ipAddress}")
    fun addQuizSetView(@PathVariable quizSetId:Long, @PathVariable ipAddress:String) {
        quizSetService.addQuizSetView(quizSetId, ipAddress)
    }

    @Operation(summary = "공유된 퀴즈셋을 추가", description = "공유된 퀴즈셋을 추가합니다.")
    @PostMapping("/addSharedQuizSet")
    fun addSharedQuizSet(@RequestBody addSharedQuizSetRequest: AddSharedQuizSetRequest){
        val memberEmail = getMemberEmail()
        sharedQuizSetService.addSharedQuizSet(memberEmail, addSharedQuizSetRequest)
    }

    @Operation(summary = "내 퀴즈셋 불러오기", description = "내 퀴즈셋을 불러옵니다.")
    @GetMapping("/getMyQuizSet")
    fun getMyQuizSet() : GetMyQuizSetResponse? {
        val memberEmail = getMemberEmail()
        val myOwnQuizSetResponse = quizSetService.myOwnQuizSet(memberEmail)
        val mySharedQuizSetResponse = sharedQuizSetService.mySharedQuizSet(memberEmail)

        return GetMyQuizSetResponse(myOwnQuizSetResponse, mySharedQuizSetResponse)
    }

    @Operation(summary = "추천 퀴즈셋 불러오기", description = "조회수 top10 퀴즈셋을 불러옵니다")
    @GetMapping("/getRecommendedQuizSet")
    fun getRecommendedQuizSet(): MutableList<QuizSetResponse> {
        return quizSetService.getFindTop10()
    }
}