package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.domain.quizset.QuizSet
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import com.whoiswoony.springtutorial.dto.AddSharedQuizSetRequest
import com.whoiswoony.springtutorial.dto.GetMySharedQuizSetResponse
import com.whoiswoony.springtutorial.service.quizset.QuizSetService
import com.whoiswoony.springtutorial.service.quizset.SharedQuizSetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="퀴즈셋", description = "퀴즈셋을 다루는 API 입니다.")
@RestController
@RequestMapping("/user/")
class QuizSetController(private val quizSetService: QuizSetService, private val sharedQuizSetService: SharedQuizSetService) {
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

    @Operation(summary = "공유된 퀴즈셋을 추가", description = "공유된 퀴즈셋을 추가합니다.")
    @PostMapping("/addSharedQuizSet")
    fun addSharedQuizSet(@RequestBody addSharedQuizSetRequest: AddSharedQuizSetRequest){
        val memberEmail = getMemberEmail()
        sharedQuizSetService.addSharedQuizSet(memberEmail, addSharedQuizSetRequest)
    }

    @Operation(summary = "내 퀴즈셋 불러오기", description = "내 퀴즈셋을 불러옵니다.")
    @GetMapping("/getMyQuizSet")
    fun getMyQuizSet() : MutableList<QuizSet>? {
        val memberEmail = getMemberEmail()
        return quizSetService.getMyQuizSet(memberEmail)
    }

    @Operation(summary = "퍼온 퀴즈셋 불러오기", description = "퍼온 퀴즈셋을 불러옵니다.")
    @GetMapping("/getMySharedQuizSet")
    fun getMySharedQuizSet() : MutableList<GetMySharedQuizSetResponse>? {
        val memberEmail = getMemberEmail()
        return sharedQuizSetService.getMySharedQuizSet(memberEmail)
    }
}