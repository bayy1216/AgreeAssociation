package com.reditus.agreeassociation.controller.user

import com.reditus.agreeassociation.dto.user.UserRes
import com.reditus.agreeassociation.global.api.ApiResponse
import com.reditus.agreeassociation.global.security.LoginUserDetails
import com.reditus.agreeassociation.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "유저 관련 API")
@RestController
class UserController(
    private val userService: UserService,
) {

    @Operation(summary = "유저 정보 조회", description = "로그인한 유저의 정보를 조회합니다.")
    @GetMapping("/api/user")
    fun getUserInfo(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails
    ):ApiResponse<UserRes.UserDto> {
        val res = userService.getUserInfo(loginUserDetails.userId)
        return ApiResponse.success(res)
    }
}