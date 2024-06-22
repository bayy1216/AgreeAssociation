package com.reditus.agreeassociation.dto.user

import com.reditus.agreeassociation.domain.user.User

class UserRes {
    data class UserDto(
        val id: Long,
        val email: String,
        val nickname: String,
        val profileImageUrl: String?,
        val createdAt: String,
    ){
        companion object {
            fun from(user: User): UserDto {
                return UserDto(
                    id = user.id!!,
                    email = user.email,
                    nickname = user.nickname,
                    profileImageUrl = user.profileImageUrl,
                    createdAt = user.createdAt.toString(),
                )
            }
        }
    }
}