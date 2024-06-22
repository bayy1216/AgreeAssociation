package com.reditus.agreeassociation.dto.auth

import com.reditus.agreeassociation.domain.user.User
import com.reditus.agreeassociation.dto.user.UserRes
import com.reditus.agreeassociation.global.jwt.JwtToken

class AuthRes {
    data class LoginResponse(
        val token: JwtToken,
        val user: UserRes.UserDto,
    ){
        companion object {
            fun from(token: JwtToken, user: User): LoginResponse {
                val userDto = UserRes.UserDto.from(user)
                return LoginResponse(token, userDto)
            }
        }
    }

    data class RefreshResponse(
        val accessToken: String,
    )
}