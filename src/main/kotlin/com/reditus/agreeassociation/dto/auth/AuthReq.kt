package com.reditus.agreeassociation.dto.auth

import com.reditus.agreeassociation.domain.user.UserCommand

class AuthReq {
    data class EmailLoginRequest(
        val email: String,
        val password: String,
    )

    data class EmailSignUpRequest(
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