package com.reditus.agreeassociation.global.exception

class ElementConflictException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)