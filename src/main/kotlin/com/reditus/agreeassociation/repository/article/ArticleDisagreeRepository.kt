package com.reditus.agreeassociation.repository.article

import com.reditus.agreeassociation.domain.article.ArticleDisagree
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleDisagreeRepository: JpaRepository<ArticleDisagree, Long> {
    fun countByArticleId(articleId: Long): Long
}