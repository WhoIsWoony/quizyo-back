package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.TodoDTO
import com.whoiswoony.springtutorial.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "투두를 만들건데요 삽질 중 입니다.")
@RestController
@RequestMapping("/")
class TodoApi(private val todoService: TodoService) {

    @Operation(summary = "인사하는 Hello Todo")
    @GetMapping("/")
    fun helloTodo(): String {
        return "Hello Todo"
    }

    @Operation(summary = "Todo를 전부 가져와보자!")
    @GetMapping("/getAllTodo")
    fun getAllTodo(): List<TodoDTO> {
        return todoService.getTodo()
    }
}
