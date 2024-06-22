package com.reditus.agreeassociation.dto.auth

import com.reditus.agreeassociation.domain.user.UserCommand

class AuthReq {
    data class EmailLogin(
        val email: String,
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