package com.reditus.agreeassociation.global.jwt

/**
 * 유효한 토큰을 나타내는 VO
 * 검증이 완료된 jwt를 저장
 */
@JvmInline
value class ValidToken(
    val value: String,
)