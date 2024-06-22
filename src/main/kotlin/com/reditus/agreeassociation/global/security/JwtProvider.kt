package com.reditus.agreeassociation.global.security

import com.reditus.agreeassociation.global.exception.NotAuthorizationException
import com.reditus.agreeassociation.global.jwt.JwtUtils
import com.reditus.agreeassociation.global.jwt.ValidToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtUtils: JwtUtils
) : AuthenticationProvider{
    /**
     * JwtAuthenticationToken을 이용하여 인증을 수행
     * 토큰이 유효하지 않으면 예외를 발생시킴
     * 유효한 경우 SecurityContextHolder에 인증 정보를 저장
     * 그후 UsernamePasswordAuthenticationToken를 반환
     */
    override fun authenticate(authentication: Authentication?): Authentication {
        val rawToken = authentication?.principal as String
        if(!jwtUtils.validateToken(rawToken)) {
            throw NotAuthorizationException("토큰이 유효하지 않습니다.")
        }

        val jwtUser = jwtUtils.extractJwtUser(ValidToken(rawToken))
        val authority = setOf(SimpleGrantedAuthority(jwtUser.role.name))

        val jwtUserToken = UsernamePasswordAuthenticationToken(jwtUser, null, authority)
        SecurityContextHolder.getContext().authentication = jwtUserToken

        return jwtUserToken
    }

    /**
     * authentication이 JwtAuthenticationToken인 경우에만 인증을 수행
     */
    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == JwtAuthenticationToken::class.java
    }
}