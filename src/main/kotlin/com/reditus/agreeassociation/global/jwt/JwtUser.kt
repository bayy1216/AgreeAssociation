package com.reditus.agreeassociation.global.jwt

data class JwtUser(
    val id: Long,
    val role: Role,
)

enum class Role {
    ADMIN,
    USER,
}
