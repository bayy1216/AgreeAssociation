package com.reditus.agreeassociation.repository.article

import com.reditus.agreeassociation.domain.article.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleRepository : JpaRepository<Article, Long> {
    @Modifying
    @Query("UPDATE Article a SET a.viewsCount = a.viewsCount + 1 WHERE a.id = :articleId")
    fun addViewsCountById(@Param("articleId") articleId: Long)
}