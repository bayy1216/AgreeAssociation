package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.ArticleCommand

class ArticleReq {
    data class Create(
        val title: String,
        val content: String,
    ) {
        fun toCommand(): ArticleCommand.Create {
            return ArticleCommand.Create(
                title = title,
                content = content,
            )
        }
    }

    data class Update(
        val title: String,
        val content: String,
    ){
        fun toCommand(): ArticleCommand.Update {
            return ArticleCommand.Update(
                title = title,
                content = content,
            )
        }
    }
}