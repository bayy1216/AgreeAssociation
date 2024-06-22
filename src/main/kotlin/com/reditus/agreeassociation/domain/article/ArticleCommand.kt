package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.util.SelfValidating
import jakarta.validation.constraints.NotBlank

class ArticleCommand {
    class Create(
        @field:NotBlank(message = "제목을 입력해주세요.")
        val title: String,
        @field:NotBlank(message = "내용을 입력해주세요.")
        val content: String,
    ): SelfValidating(){
        init {
            this.validateSelf()
        }
    }

    class Update(
        @field:NotBlank(message = "제목을 입력해주세요.")
        val title: String,
        @field:NotBlank(message = "내용을 입력해주세요.")
        val content: String,
    ): SelfValidating(){
        init {
            this.validateSelf()
        }
    }

    enum class PagingSort{
        CREATED_AT_DESC,
        CREATED_AT_ASC,
        VIEWS_COUNT_DESC,
        VIEWS_COUNT_ASC,
        AGREE_COUNT_DESC,
        AGREE_COUNT_ASC,
        DISAGREE_COUNT_DESC,
        DISAGREE_COUNT_ASC,
    }
}