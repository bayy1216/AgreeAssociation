package com.reditus.agreeassociation.global.exception

class NotAuthorizationException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
}