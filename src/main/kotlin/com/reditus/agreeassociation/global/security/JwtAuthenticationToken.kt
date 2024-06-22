package com.reditus.agreeassociation.global.security

import org.springframework.security.authentication.AbstractAuthenticationToken

/**
 * Jwt 토큰을 이용한 인증을 위한 AuthenticationToken
 * UsernamePasswordAuthenticationToken를 참고하여 구현
 * @param rawToken 인증전 JWT 토큰
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 */
class JwtAuthenticationToken(
    private val rawToken: String,
) : AbstractAuthenticationToken(null) {
    init {
        this.isAuthenticated = false
    }


    override fun getCredentials(): Any {
        return rawToken
    }

    override fun getPrincipal(): Any {
        return rawToken
    }
}