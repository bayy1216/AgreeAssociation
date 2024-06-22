package com.reditus.agreeassociation.repository.article

import com.querydsl.jpa.impl.JPAQueryFactory
import com.reditus.agreeassociation.domain.article.ArticleComment
import com.reditus.agreeassociation.domain.article.QArticleComment
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ArticleCommentQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    /**
     * 페이징 처리된 게시글 댓글 목록 조회
     * 정렬 조건
     * 1. parentPath 오름차순(ex / -> /1/2 -> /1/3 -> /1/4 -> /2/1 -> /2/2 -> /2/3)
     * 2. 생성일 오름차순
     */
    fun pagingArticleComment(
        articleId: Long,
        pageable: Pageable,
    ): List<ArticleComment> {
        return queryFactory
            .selectFrom(QArticleComment.articleComment)
            .where(QArticleComment.articleComment.article.id.eq(articleId))
            .join(QArticleComment.articleComment.author).fetchJoin()
            .orderBy(QArticleComment.articleComment.parentPath.asc(), QArticleComment.articleComment.createdAt.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

    }
}