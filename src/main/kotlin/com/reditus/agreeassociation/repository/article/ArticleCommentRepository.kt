package com.reditus.agreeassociation.repository.article

import com.reditus.agreeassociation.domain.article.ArticleComment
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleCommentRepository : JpaRepository<ArticleComment, Long> {
}