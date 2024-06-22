package com.reditus.agreeassociation.domain.user

import com.reditus.agreeassociation.domain.BaseTimeEntity
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
    var point: Int,
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

class UserCommand{
    class Create(
        val email: String,
        val password: String,
        val nickname: String,
    )
    class Patch(
        val nickname: String?,
        val profileImageUrl: String?,
    )
}