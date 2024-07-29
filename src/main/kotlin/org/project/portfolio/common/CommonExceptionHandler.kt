package org.project.portfolio.common

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice // 이 클래스가 전역적인 Exception Handler임을 알림
@RestController
class CommonExceptionHandler {
    private val looger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CommonException::class)
    fun handleCommonException(e: CommonException): ResponseEntity<String> {
        looger.error("API error", e)
        //return ApiResponse.error(e.message)
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        looger.error("API error", e)
        return ResponseEntity("알 수 없는 오류가 발생했습니다.", HttpStatus.BAD_REQUEST)
    }
}