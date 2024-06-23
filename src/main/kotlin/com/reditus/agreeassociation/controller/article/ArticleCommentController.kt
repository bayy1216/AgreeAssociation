package com.reditus.agreeassociation.controller.article

import com.reditus.agreeassociation.dto.PagingReq
import com.reditus.agreeassociation.dto.article.ArticleCommentReq
import com.reditus.agreeassociation.dto.article.ArticleCommentRes
import com.reditus.agreeassociation.global.api.ApiResponse
import com.reditus.agreeassociation.global.security.LoginUserDetails
import com.reditus.agreeassociation.service.article.ArticleCommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "ArticleComment", description = "게시글 댓글 관련 API")
@RestController
class ArticleCommentController(
    private val articleCommentService: ArticleCommentService
) {
    @Operation(summary = "게시글 댓글 작성", description = "게시글 댓글을 작성합니다.")
    @PostMapping("/api/article/{articleId}/comment")
    fun createArticleComment(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails,
        @PathVariable articleId: Long,
        @Valid @RequestBody request: ArticleCommentReq.Create
    ) : ApiResponse<Long>{
        val id = articleCommentService.createArticleComment(articleId, loginUserDetails.userId, request)
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