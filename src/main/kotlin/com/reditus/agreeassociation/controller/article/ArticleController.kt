package com.reditus.agreeassociation.controller.article

import com.reditus.agreeassociation.service.article.ArticleService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Article", description = "게시글 관련 API")
@RestController
class ArticleController(
    private val articleService: ArticleService
) {
}