package com.reditus.agreeassociation.global.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    servers = [
        Server(url = "https://api.reditus.site", description = "Test Server"),
        Server(url = "http://localhost:8080", description = "localhost"),
    ]
)
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(components())
        .info(apiInfo())
        .addSecurityItem(SecurityRequirement().addList("bearer-key"))

    private fun components() = Components()
        .addSecuritySchemes("bearer-key", bearerJwtSecurityScheme())

    private fun bearerJwtSecurityScheme() = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("JWT 인증을 위한 토큰을 입력하세요. (예: Bearer {token}에서 token만 입력)")

    private fun apiInfo() = Info()
        .title("AgreeAssociation API")
        .description("api 문서입니다.")
        .version("0.0.1")
}