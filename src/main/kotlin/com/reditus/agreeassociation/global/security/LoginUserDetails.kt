package com.reditus.agreeassociation.global.security

import com.reditus.agreeassociation.global.jwt.JwtUser
import com.reditus.agreeassociation.global.jwt.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LoginUserDetails(
    private val jwtUser: JwtUser
) : UserDetails {
    val userId: Long
        get() = jwtUser.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_${jwtUser.role.name}"))
    }

    override fun getPassword(): String {
        return jwtUser.id.toString()
    }

    override fun getUsername(): String {
        return jwtUser.id.toString()
    }

}
