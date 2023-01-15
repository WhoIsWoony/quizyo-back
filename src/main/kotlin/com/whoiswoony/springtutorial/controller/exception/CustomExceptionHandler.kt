package com.whoiswoony.springtutorial.controller.exception

import com.whoiswoony.springtutorial.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.errorCode.status)
            .body(ErrorResponse(e.errorCode.status.value(), e.errorCode.message))
    }
}