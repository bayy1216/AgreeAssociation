package com.reditus.agreeassociation.service.article

import com.reditus.agreeassociation.domain.article.ArticleComment
import com.reditus.agreeassociation.dto.article.ArticleCommentReq
import com.reditus.agreeassociation.dto.article.ArticleCommentRes
import com.reditus.agreeassociation.repository.article.ArticleCommentQueryRepository
import com.reditus.agreeassociation.repository.article.ArticleCommentRepository
import com.reditus.agreeassociation.repository.article.ArticleRepository
import com.reditus.agreeassociation.repository.findByIdOrThrow
import com.reditus.agreeassociation.repository.user.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleCommentService(
    private val articleCommentRepository: ArticleCommentRepository,
    private val articleCommentQueryRepository: ArticleCommentQueryRepository,
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
) {
    /**
     * 게시글 댓글을 작성합니다.
     * 1. 사용자, 게시글 정보를 조회합니다.
     * 2. 상위 댓글이 없다면, 루트 경로로 댓글을 생성합니다.
     * 3. 상위 댓글이 있다면, 상위 댓글의 경로를 참조하여 댓글을 생성합니다.
     * 4. 댓글을 저장하고, 댓글의 ID를 반환합니다.
     */
    @Transactional
    fun createArticleComment(articleId: Long, userId: Long, request: ArticleCommentReq.Create): Long {
        val user = userRepository.getReferenceById(userId)
        val article = articleRepository.getReferenceById(articleId)

        val command = if(request.parentCommentId != null){
            val parentComment = articleCommentRepository.findByIdOrThrow(request.parentCommentId)
            request.toCommand(parentComment.getPath())
        } else {
            request.toCommand(ArticleComment.getRootPath())
        }

        val comment = ArticleComment.create(command = command, author = user, article = article)
        articleCommentRepository.save(comment)
        return comment.id!!
    }

    @Transactional(readOnly = true)
    fun getArticleCommentPaging(
        articleId: Long,
        pageable: Pageable
    ): List<ArticleCommentRes.ArticleCommentDto> {
        val comments = articleCommentQueryRepository.pagingArticleComment(articleId, pageable)
        return comments.map { ArticleCommentRes.ArticleCommentDto.from(it) }
    }

}