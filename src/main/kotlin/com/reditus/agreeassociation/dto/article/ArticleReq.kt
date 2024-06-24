package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.ArticleCommand
import io.swagger.v3.oas.annotations.media.Schema

class ArticleReq {
    @Schema(name = "ArticleReq.Create")
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

    @Schema(name = "ArticleReq.Update")
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