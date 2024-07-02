package com.reditus.agreeassociation.service.auth

import com.reditus.agreeassociation.domain.user.User
import com.reditus.agreeassociation.dto.auth.AuthReq
import com.reditus.agreeassociation.dto.auth.AuthRes
import com.reditus.agreeassociation.global.exception.ElementConflictException
import com.reditus.agreeassociation.global.exception.InvalidPasswordException
import com.reditus.agreeassociation.global.exception.NoAuthenticationException
import com.reditus.agreeassociation.global.jwt.JwtUser
import com.reditus.agreeassociation.global.jwt.JwtUtils
import com.reditus.agreeassociation.global.jwt.ValidToken
import com.reditus.agreeassociation.repository.user.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.swing.text.Element

@Service
class AuthService(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    /**
     * 이메일 회원가입
     * 1. 이미 가입된 이메일인지 확인
     * 2. 비밀번호를 암호화하여 저장
     * 3. 토큰을 생성하여 반환
     */
    fun signup(request: AuthReq.EmailSignUp): AuthRes.LoginResponse {
        if(userRepository.existsByEmail(request.email)) {
            throw ElementConflictException("이미 가입된 이메일입니다.")
        }
        val command = request.toCommand(bCryptPasswordEncoder.encode(request.password))
        val user = User.create(command)
        userRepository.save(user)
        val token = jwtUtils.createToken(JwtUser(user.id!!, user.role))
        return AuthRes.LoginResponse.from(token, user)
    }

    /**
     * 이메일 로그인
     * 1. 이메일로 유저를 조회
     * 2. 비밀번호가 일치하는지 확인
     * 3. 토큰을 생성하여 반환
     */
    fun emailLogin(request: AuthReq.EmailLogin): AuthRes.LoginResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw NoSuchElementException("가입되지 않은 이메일입니다.")
        if(!bCryptPasswordEncoder.matches(request.password, user.password)) {
            throw InvalidPasswordException(message= "비밀번호가 일치하지 않습니다.", userEmail = request.email)
        }
        val token = jwtUtils.createToken(JwtUser(user.id!!, user.role))
        return AuthRes.LoginResponse.from(token, user)
    }

    /**
     * 토큰을 검증하고 새로운 토큰을 발급한다.
     * - 현재는 DB의 유저를 조회하지 않고 토큰만 검증
     * - 추후 DB에서 유저의 유효성을 검증해야 할수도 있음
     */
    fun refresh(rawRefreshToken: String): AuthRes.RefreshResponse {
        jwtUtils.validateToken(rawRefreshToken)
        val jwtUser = jwtUtils.extractJwtUser(ValidToken(rawRefreshToken))
        val newToken = jwtUtils.createToken(jwtUser)
        return AuthRes.RefreshResponse(newToken.accessToken)
    }
}