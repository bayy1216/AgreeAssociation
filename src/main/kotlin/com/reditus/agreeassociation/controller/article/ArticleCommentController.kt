package com.reditus.agreeassociation.controller.article

import com.reditus.agreeassociation.dto.PagingReq
import com.reditus.agreeassociation.dto.article.ArticleCommentReq
import com.reditus.agreeassociation.dto.article.ArticleCommentRes
import com.reditus.agreeassociation.global.api.ApiResponse
import com.reditus.agreeassociation.global.jwt.JwtUser
import com.reditus.agreeassociation.service.article.ArticleCommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "ArticleComment", description = "게시글 댓글 관련 API")
@RestController
class ArticleCommentController(
    private val articleCommentService: ArticleCommentService
) {
    @Operation(summary = "게시글 댓글 작성", description = "게시글 댓글을 작성합니다.")
    @PostMapping("/api/article/{articleId}/comment")
    fun createArticleComment(
        @PathVariable articleId: Long,
        @AuthenticationPrincipal jwtUser: JwtUser,
        @Valid @RequestBody request: ArticleCommentReq.Create
    ) : ApiResponse<Long>{
        val id = articleCommentService.createArticleComment(articleId, jwtUser.id, request)
        return ApiResponse.success(id)
    }

    @GetMapping("/api/article/{articleId}/comment")
    fun getArticleComments(
        @PathVariable articleId: Long,
        pagingReq: PagingReq
    ) : ApiResponse<List<ArticleCommentRes.ArticleCommentDto>>{
        val comments = articleCommentService.getArticleCommentPaging(articleId, pagingReq.toPageable())
        return ApiResponse.success(comments)
    }
}