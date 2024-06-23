package com.reditus.agreeassociation.global.jwt

import com.reditus.agreeassociation.global.exception.NoAuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expire-time}") private val expiration: Long,
    @Value("\${jwt.refresh-token-expire-time}") private val refreshExpiration: Long,
) {
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) }

    /**
     * JwtUser를 받아 토큰을 생성
     * accessToken과 refreshToken을 생성하여 JwtToken을 반환
     */
    fun createToken(jwtUser: JwtUser): JwtToken {
        val accessToken = generateToken(jwtUser, isAccessToken = true)
        val refreshToken = generateToken(jwtUser, isAccessToken = false)
        return JwtToken(accessToken, refreshToken)
    }

    /**
     * JwtUser를 받아 토큰을 생성
     * accessToken 혹은 refreshToken을 생성하여 반환
     */
    private fun generateToken(jwtUser: JwtUser, isAccessToken: Boolean): String{
        val now = Date()
        val expirationTime = if(isAccessToken) now.time + expiration else now.time + refreshExpiration
        return Jwts.builder()
            .subject(jwtUser.id.toString())
            .claim(ROLE, jwtUser.role.name)
            .claim(IS_ACCESS_TOKEN, isAccessToken)
            .issuedAt(now)
            .issuer(ISSUER)
            .expiration(Date(expirationTime))
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }

    /**
     * 유효성 검사가 완료된 토큰을 받아 JwtUser를 추출
     */
    fun extractJwtUser(token: ValidToken): JwtUser {
        val claims = extractClaims(token.value)
        return JwtUser(
            id = claims.subject.toLong(),
            role = Role.valueOf(claims[ROLE] as String)
        )
    }


    fun validateToken(rawToken: String, isAccessToken:Boolean = true) : Boolean {
        val claims = extractClaims(rawToken)
        val expiration = claims.expiration
        if(isAccessToken != claims[IS_ACCESS_TOKEN] as Boolean){
            return false
        }
        return expiration.after(Date())
    }
    private fun extractClaims(rawToken: String): Claims {
        try{
            return Jwts.parser().verifyWith(key).build().parse(rawToken).payload as Claims
        }catch (e: Exception){
            throw NoAuthenticationException("토큰이 유효하지 않습니다.")
        }
    }

    companion object {
        const val ROLE = "role"
        const val IS_ACCESS_TOKEN = "isAccess"
        const val ISSUER = "reditus"
    }
}