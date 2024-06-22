package com.reditus.agreeassociation.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class PagingReq(
    val page: Int,
    val size: Int = 20,
) {
    fun toPageable(): Pageable {
        return PageRequest.of(page, size)
    }
}