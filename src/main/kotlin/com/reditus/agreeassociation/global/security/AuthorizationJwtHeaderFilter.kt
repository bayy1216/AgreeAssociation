package com.reditus.agreeassociation.global.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


class AuthorizationJwtHeaderFilter(authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {

    /**
     * Request Header에서 Authorization을 확인하여 JWT 토큰을 추출하고 인증을 시도하는 메소드
     * Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우 필터를 통과시키고
     * Bearer 토큰인 경우 JwtAuthenticationToken을 생성하여 인증을 시도한다.
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }
        if(super.shouldNotFilter(request)) {
            chain.doFilter(request, response)
            return
        }
        val rawToken = header.replace("Bearer ", "")
        val auth = JwtAuthenticationToken(rawToken)
        super.getAuthenticationManager().authenticate(auth) // Manager에게 JwtAuthenticationToken을 전달하여 인증을 시도
        chain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI.contains("/api/auth/")
    }
}