package com.reditus.agreeassociation.controller.article

import com.reditus.agreeassociation.service.article.ArticleCommentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController

@Tag(name = "ArticleComment", description = "게시글 댓글 관련 API")
@RestController
class ArticleCommentController(
    private val articleCommentService: ArticleCommentService
) {
}