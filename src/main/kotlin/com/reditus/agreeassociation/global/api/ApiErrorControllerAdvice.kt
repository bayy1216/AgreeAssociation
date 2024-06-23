package com.reditus.agreeassociation.global.api

import com.reditus.agreeassociation.global.exception.NoAuthenticationException
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiErrorControllerAdvice {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException(e: IllegalArgumentException): ApiResponse<Unit> {
        log.info("IllegalArgumentException", e)
        return ApiResponse.fail(
            message = e.message ?: "COMMON-ILLEGAL-ARGUMENT-EXCEPTION",
            errorCode = "COMMON-ILLEGAL-ARGUMENT",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun constraintViolationException(e: ConstraintViolationException): ApiResponse<Unit> {
        log.info("ConstraintViolationException", e)
        return ApiResponse.fail(
            message = e.message ?: "COMMON-CONSTRAINT-VIOLATION-EXCEPTION",
            errorCode = "COMMON-CONSTRAINT-VIOLATION",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalStateException(e: IllegalStateException): ApiResponse<Unit> {
        log.error("IllegalStateException", e)
        return ApiResponse.fail(
            message = e.message ?: "COMMON-ILLEGAL-STATE-EXCEPTION",
            errorCode = "COMMON-ILLEGAL-STATE",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun jwtException(e: NoAuthenticationException): ApiResponse<Unit> {
        return ApiResponse.fail(
            message = e.message ?: "COMMON-UNAUTHORIZED-EXCEPTION",
            errorCode = "UNAUTHORIZED",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundException(e: NoSuchElementException): ApiResponse<Unit> {
        log.error("NoSuchElementException", e)
        return ApiResponse.fail(
            message = "해당 리소스를 찾을 수 없습니다.",
            errorCode = "NOT-FOUND",
        )
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun runtimeException(e: RuntimeException): ApiResponse<Unit> {
        log.error("RuntimeException", e)
        return ApiResponse.fail(
            message = "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
            errorCode = "COMMON-RUNTIME-EXCEPTION",
        )
    }
}