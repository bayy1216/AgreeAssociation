package com.reditus.agreeassociation.domain.user

import com.reditus.agreeassociation.global.jwt.Role
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var role: Role,

    var email: String,
    var password: String,
    var nickname: String,
) {
}