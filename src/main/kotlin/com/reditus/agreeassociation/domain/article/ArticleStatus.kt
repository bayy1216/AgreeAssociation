package com.reditus.agreeassociation.domain.article

enum class ArticleStatus(
    val korean: String,
) {
    HONORED("명예의 전당"),
    PENDING("승인 대기 중"),
    NOTHING("기본 상태"),
}