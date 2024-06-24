package com.reditus.agreeassociation.dto.auth

import com.reditus.agreeassociation.domain.user.UserCommand
import io.swagger.v3.oas.annotations.media.Schema

class AuthReq {
    data class EmailLogin(
        @Schema(example = "test@a.c")
        val email: String,
        @Schema(example = "test")
        val password: String,
    )

    data class EmailSignUp(
        val email: String,
        val password: String,
        val nickname: String,
    ) {
        fun toCommand(encode: String): UserCommand.Create {
            return UserCommand.Create(
                email = email,
                password = encode,
                nickname = nickname,
            )
        }
    }
}