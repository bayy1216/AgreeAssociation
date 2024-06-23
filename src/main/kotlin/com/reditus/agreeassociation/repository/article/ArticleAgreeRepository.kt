package com.reditus.agreeassociation.repository.article

import com.reditus.agreeassociation.domain.article.ArticleAgree
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleAgreeRepository: JpaRepository<ArticleAgree, Long> {
    fun countByArticleId(articleId: Long): Long
    fun existsByUserIdAndArticleId(userId: Long, articleId: Long): Boolean
}