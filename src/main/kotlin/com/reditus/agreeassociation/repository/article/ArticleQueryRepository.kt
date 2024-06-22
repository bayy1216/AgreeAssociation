package com.reditus.agreeassociation.repository.article

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import com.reditus.agreeassociation.domain.article.Article
import com.reditus.agreeassociation.domain.article.ArticleCommand
import com.reditus.agreeassociation.domain.article.QArticle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ArticleQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun getArticlePaging(
        pageable: Pageable,
        sort: ArticleCommand.PagingSort
    ) : List<Article>{
        return queryFactory
            .selectFrom(QArticle.article)
            .join(QArticle.article.author).fetchJoin()
            .orderBy(sortSpec(sort))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    private fun sortSpec(sort: ArticleCommand.PagingSort): OrderSpecifier<*> {
        return  when(sort) {
            ArticleCommand.PagingSort.CREATED_AT_DESC -> QArticle.article.createdAt.desc()
            ArticleCommand.PagingSort.CREATED_AT_ASC -> QArticle.article.createdAt.asc()
            ArticleCommand.PagingSort.VIEWS_COUNT_DESC -> QArticle.article.viewsCount.desc()
            ArticleCommand.PagingSort.VIEWS_COUNT_ASC -> QArticle.article.viewsCount.asc()
            ArticleCommand.PagingSort.AGREE_COUNT_DESC -> TODO()
            ArticleCommand.PagingSort.AGREE_COUNT_ASC -> TODO()
            ArticleCommand.PagingSort.DISAGREE_COUNT_DESC -> TODO()
            ArticleCommand.PagingSort.DISAGREE_COUNT_ASC -> TODO()
        }
    }
}