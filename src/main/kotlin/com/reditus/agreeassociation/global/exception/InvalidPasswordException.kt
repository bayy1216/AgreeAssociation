package com.reditus.agreeassociation.global.exception

class InvalidPasswordException(
    message: String,
    val userEmail: String,
): RuntimeException(message) {
}