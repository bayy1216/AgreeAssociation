package com.reditus.agreeassociation.controller.auth

import com.reditus.agreeassociation.dto.auth.AuthReq
import com.reditus.agreeassociation.dto.auth.AuthRes
import com.reditus.agreeassociation.global.api.ApiResponse
import com.reditus.agreeassociation.service.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "회원가입", description = "회원가입 후, jwt와 user 정보를 반환합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/auth/signup")
    fun signup(
        @Valid @RequestBody request: AuthReq.EmailSignUp
    ) : ApiResponse<AuthRes.LoginResponse>{
        val res =  authService.signup(request)
        return ApiResponse.success(res)
    }

    @Operation(summary = "이메일 로그인", description = "jwt와 user 정보를 반환합니다.")
    @PostMapping("/api/auth/login")
    fun emailLogin(
        @Valid @RequestBody request: AuthReq.EmailLogin
    ) : ApiResponse<AuthRes.LoginResponse>{
        val res =  authService.emailLogin(request)
        return ApiResponse.success(res)
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token을 이용하여 새로운 Access Token을 발급합니다.")
    @PostMapping("/api/auth/refresh")
    fun refresh(
        @RequestHeader("Authorization") authHeader: String
    ): ApiResponse<AuthRes.RefreshResponse> {
        if(!authHeader.startsWith("Bearer ")) {
            throw IllegalArgumentException("토큰이 올바르지 않습니다.")
        }
        val rawRefreshToken = authHeader.replace("Bearer ", "")
        val res = authService.refresh(rawRefreshToken)
        return ApiResponse.success(res)
    }
}