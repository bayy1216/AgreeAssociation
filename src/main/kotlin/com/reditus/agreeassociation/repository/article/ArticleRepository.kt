package com.reditus.agreeassociation.repository.article

import com.reditus.agreeassociation.domain.article.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
}