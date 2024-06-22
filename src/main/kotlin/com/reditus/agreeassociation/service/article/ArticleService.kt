package com.reditus.agreeassociation.service.article

import com.reditus.agreeassociation.domain.article.Article
import com.reditus.agreeassociation.domain.article.ArticleCommand
import com.reditus.agreeassociation.dto.PagingReq
import com.reditus.agreeassociation.dto.article.ArticleReq
import com.reditus.agreeassociation.dto.article.ArticleRes
import com.reditus.agreeassociation.repository.article.ArticleQueryRepository
import com.reditus.agreeassociation.repository.article.ArticleRepository
import com.reditus.agreeassociation.repository.findByIdOrThrow
import com.reditus.agreeassociation.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val articleQueryRepository: ArticleQueryRepository,
) {

    @Transactional
    fun createArticle(userId: Long,req: ArticleReq.Create) : Long {
        val command = req.toCommand()
        val user = userRepository.findByIdOrThrow(userId)
        val article = Article.create(command = command, author = user)
        articleRepository.save(article)
        return article.id!!
    }

    @Transactional
    fun updateArticle(userId:Long, articleId: Long, req: ArticleReq.Update) {
        val command = req.toCommand()
        val user = userRepository.findByIdOrThrow(userId)
        val article = articleRepository.findByIdOrThrow(articleId)
        if(article.author.id != user.id) {
            throw IllegalArgumentException("작성자만 수정할 수 있습니다.")
        }
        article.update(command)
    }

    @Transactional(readOnly = true)
    fun getArticlePaging(req: PagingReq, sort: ArticleCommand.PagingSort): List<ArticleRes.ArticleDto> {
        val articlePage = articleQueryRepository.getArticlePaging(req.toPageable(), sort)
        return articlePage.map(ArticleRes.ArticleDto::from)
    }
}