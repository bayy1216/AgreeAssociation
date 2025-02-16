package com.reditus.agreeassociation.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class JpaConfig(
    private val em: EntityManager
) {
    @Bean
    fun querydsl(): JPAQueryFactory = JPAQueryFactory(em)
}
