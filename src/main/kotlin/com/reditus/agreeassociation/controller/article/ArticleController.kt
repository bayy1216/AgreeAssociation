package com.reditus.agreeassociation.controller.article

import com.reditus.agreeassociation.domain.article.ArticleCommand
import com.reditus.agreeassociation.dto.PagingReq
import com.reditus.agreeassociation.dto.article.ArticleReq
import com.reditus.agreeassociation.dto.article.ArticleRes
import com.reditus.agreeassociation.global.api.ApiResponse
import com.reditus.agreeassociation.global.security.LoginUserDetails
import com.reditus.agreeassociation.service.article.ArticleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "Article", description = "게시글 관련 API")
@RestController
class ArticleController(
    private val articleService: ArticleService
) {
    @Operation(summary = "게시글 작성", description = "게시글을 작성 후, 게시글 id 반환.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/articles")
    fun createArticle(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails,
        @Valid @RequestBody request: ArticleReq.Create
    ) : ApiResponse<Long> {
        val res =  articleService.createArticle(loginUserDetails.userId, request.toCommand())
        return ApiResponse.success(res)
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PostMapping("/api/articles/{articleId}")
    fun updateArticle(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails,
        @Valid @RequestBody request: ArticleReq.Update,
        @PathVariable articleId: Long
    ) : ApiResponse<Unit> {
        articleService.updateArticle(loginUserDetails.userId, articleId, request.toCommand())
        return ApiResponse.success(Unit)
    }

    @Operation(summary = "게시글 페이징 조회", description = "게시글을 페이징 조회합니다.")
    @GetMapping("/api/articles")
    fun getArticlePaging(
        req: PagingReq,
        sort: ArticleCommand.PagingSort = ArticleCommand.PagingSort.CREATED_AT_DESC
    ) : ApiResponse<List<ArticleRes.ArticleDto>> {
        val res =  articleService.getArticlePaging(req, sort)
        return ApiResponse.success(res)
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @GetMapping("/api/articles/{articleId}")
    fun getArticleDetail(
        @PathVariable articleId: Long,
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails?,
    ) : ApiResponse<ArticleRes.ArticleDetailDto> {
        val res =  articleService.getArticleDetail(articleId ,loginUserDetails?.userId)
        return ApiResponse.success(res)
    }

    @Operation(summary = "게시글 인정", description = "게시글을 인정 후, 인정 수 반환.")
    @PostMapping("/api/articles/{articleId}/agree")
    fun agreeArticle(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails,
        @PathVariable articleId: Long
    ) : ApiResponse<Long> {
        val res =  articleService.agreeArticle(loginUserDetails.userId, articleId)
        return ApiResponse.success(res)
    }

    @Operation(summary = "게시글 비인정", description = "게시글을 비인정 후, 인정 수 반환.")
    @PostMapping("/api/articles/{articleId}/disagree")
    fun disagreeArticle(
        @AuthenticationPrincipal loginUserDetails: LoginUserDetails,
        @PathVariable articleId: Long
    ) : ApiResponse<Long> {
        val res =  articleService.disagreeArticle(loginUserDetails.userId, articleId)
        return ApiResponse.success(res)
    }
}