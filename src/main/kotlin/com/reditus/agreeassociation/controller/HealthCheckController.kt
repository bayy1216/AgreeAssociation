package com.reditus.agreeassociation.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(
    private val environment: Environment
) {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "OK ${environment.activeProfiles.joinToString()}"
    }
}