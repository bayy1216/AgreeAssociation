package com.reditus.agreeassociation.dto.article

import com.reditus.agreeassociation.domain.article.ArticleCommentCommand
import io.swagger.v3.oas.annotations.media.Schema

class ArticleCommentReq {
    @Schema(name = "ArticleCommentReq.Create") // 동일한 Class 이름이 있을 경우, 네임스페이스가 다르더라도 내부 클래스 이름이 같아서 충돌이 발생할 수 있습니다.
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