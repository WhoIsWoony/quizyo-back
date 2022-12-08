package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "QuizS : Space API")
@RestController
@RequestMapping("/")
class SpaceApi(private val todoService: TodoService) {

    @Operation(summary = "내가속한그룹불러오기")
    @GetMapping("/findAllSpace")
    fun findAllSpace(
        @RequestParam accessToken:String,
    ): Boolean {
        return true
    }
}
