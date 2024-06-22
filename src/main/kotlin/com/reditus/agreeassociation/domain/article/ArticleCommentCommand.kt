package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.util.SelfValidating
import jakarta.validation.constraints.NotBlank

class ArticleCommentCommand {
    class Create(
        @field:NotBlank(message = "내용을 입력해주세요.")
        val content: String,
        val parentPath: String,
    ) : SelfValidating() {
        init {
            this.validateSelf()
            validatePath()
        }

        private fun validatePath() {
            if (parentPath.isBlank()) {
                throw InternalError("path는 비어있을 수 없습니다.")
            }
            if (!parentPath.endsWith("/")) {
                throw InternalError("path는 /를 포함해야 합니다.")
            }
        }
    }
}