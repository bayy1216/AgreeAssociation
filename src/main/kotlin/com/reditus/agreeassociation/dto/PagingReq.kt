package com.reditus.agreeassociation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class PagingReq(
    val page: Int,
    @Schema(defaultValue = "20")
    val size: Int = 20,
) {
    fun toPageable(): Pageable {
        return PageRequest.of(page, size)
    }
}