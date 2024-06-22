package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.Article
import com.reditus.agreeassociation.domain.article.ArticleStatus
import com.reditus.agreeassociation.dto.user.UserRes
import java.time.LocalDateTime

class ArticleRes {
    data class ArticleDto(
        val id: Long,
        val title: String,
        val status: ArticleStatus,
        val viewsCount: Long,
        val createdAt: LocalDateTime,
        val author: UserRes.UserInfoDto,
    ){
        companion object {
            fun from(article: Article): ArticleDto {
                val author = UserRes.UserInfoDto.from(article.author)
                return ArticleDto(
                    id = article.id!!,
                    title = article.title,
                    status = article.articleStatus,
                    viewsCount = article.viewsCount,
                    createdAt = LocalDateTime.from(article.createdAt),
                    author = author,
                )
            }
        }
    }

    data class ArticleDetailDto(
        val id: Long,
        val title: String,
        val content: String,
        val status: String,
        val viewsCount: Long,
        val createdAt: String,
        val author: UserRes.UserInfoDto,
    )
}