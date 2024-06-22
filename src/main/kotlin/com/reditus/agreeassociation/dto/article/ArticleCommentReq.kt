package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.ArticleCommentCommand

class ArticleCommentReq {
    data class Create(
        val content: String,
        val parentCommentId: Long?,
    ){
        fun toCommand(parentPath: String): ArticleCommentCommand.Create{
            return ArticleCommentCommand.Create(
                content = this.content,
                parentPath = parentPath
            )
        }
    }
}