package com.reditus.agreeassociation.domain.user

import com.reditus.agreeassociation.domain.BaseTimeEntity
import com.reditus.agreeassociation.domain.util.SelfValidating
import com.reditus.agreeassociation.global.jwt.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    var role: Role,

    @Column(unique = true, nullable = false, length = 255)
    var email: String,
    @Column(nullable = false, length = 255)
    var password: String,
    @Column(nullable = false, length = 20)
    var nickname: String,
    @Column(nullable = false)
    var point: Int,
    @Column(length = 150)
    var profileImageUrl: String?,
) : BaseTimeEntity(){

    fun patch(command: UserCommand.Patch){
        command.nickname?.let { nickname = it }
        command.profileImageUrl?.let { profileImageUrl = it }
    }

    companion object {
        fun create(command: UserCommand.Create): User {
            return User(
                role = Role.USER,
                email = command.email,
                password = command.password,
                nickname = command.nickname,
                point = 0,
                profileImageUrl = null,
            )
        }
    }
}