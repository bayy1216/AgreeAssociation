package com.reditus.agreeassociation.global.jwt

data class JwtToken(
    val accessToken : String,
    val refreshToken : String,
)
