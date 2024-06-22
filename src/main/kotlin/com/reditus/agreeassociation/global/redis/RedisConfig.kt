//package com.reditus.agreeassociation.global.redis
//
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.serializer.StringRedisSerializer
//
//@Configuration
//class RedisConfig(
//    private val redisProperties: RedisProperties,
//) {
//
//    @Bean
//    fun redisTemplate(): RedisTemplate<String, String> {
//        val redisTemplate = RedisTemplate<String, String>()
//        redisTemplate.connectionFactory = redisConnectionFactory()
//        redisTemplate.keySerializer = StringRedisSerializer()
//        redisTemplate.valueSerializer = StringRedisSerializer()
//        return redisTemplate
//    }
//
//    @Bean
//    fun redisConnectionFactory(): LettuceConnectionFactory {
//        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
//    }
//
//
//}