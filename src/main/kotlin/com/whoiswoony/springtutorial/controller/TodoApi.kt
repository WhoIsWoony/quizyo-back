package com.whoiswoony.springtutorial.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class TodoApi {

    @GetMapping("/")
    fun helloTodo(): String {
        return "Hello Todo"
    }
}
