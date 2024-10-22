package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.ArticleComment
import com.reditus.agreeassociation.dto.user.UserRes
import java.time.LocalDateTime

class ArticleCommentRes {
    data class ArticleCommentDto(
        val id: Long,
        val content: String,
        val parentPath: String,
        val createdAt: LocalDateTime,
        val author: UserRes.UserInfoDto,
    ){
        companion object{
            fun from(comment: ArticleComment): ArticleCommentDto{
                val authorDto = UserRes.UserInfoDto.from(comment.author)
                return ArticleCommentDto(
                    id = comment.id!!,
                    content = comment.content,
                    parentPath = comment.parentPath,
                    createdAt = comment.createdAt!!,
                    author = authorDto
                )
            }
        }
    }
}