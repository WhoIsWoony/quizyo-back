package com.whoiswoony.springtutorial.service

import com.whoiswoony.springtutorial.TodoDTO
import com.whoiswoony.springtutorial.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {
    fun getTodo(): List<TodoDTO> {
        return todoRepository.findAll().map{it.toDto()}
    }
}