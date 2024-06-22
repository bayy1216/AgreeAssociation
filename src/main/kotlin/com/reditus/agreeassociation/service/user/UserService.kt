package com.reditus.agreeassociation.service.user

import com.reditus.agreeassociation.dto.user.UserRes
import com.reditus.agreeassociation.repository.findByIdOrThrow
import com.reditus.agreeassociation.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun getUserInfo(userId: Long): UserRes.UserDto {
        val user = userRepository.findByIdOrThrow(userId)
        return UserRes.UserDto.from(user)
    }
}