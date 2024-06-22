package com.reditus.agreeassociation.repository.user

import com.reditus.agreeassociation.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}