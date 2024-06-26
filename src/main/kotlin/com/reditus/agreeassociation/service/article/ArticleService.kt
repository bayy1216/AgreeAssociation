package com.reditus.agreeassociation.service.article

import com.reditus.agreeassociation.domain.article.Article
import com.reditus.agreeassociation.domain.article.ArticleAgree
import com.reditus.agreeassociation.domain.article.ArticleCommand
import com.reditus.agreeassociation.domain.article.ArticleDisagree
import com.reditus.agreeassociation.dto.PagingReq
import com.reditus.agreeassociation.dto.article.ArticleReq
import com.reditus.agreeassociation.dto.article.ArticleRes
import com.reditus.agreeassociation.repository.article.ArticleAgreeRepository
import com.reditus.agreeassociation.repository.article.ArticleDisagreeRepository
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
    private val articleAgreeRepository: ArticleAgreeRepository,
    private val articleDisagreeRepository: ArticleDisagreeRepository,
) {

    @Transactional
    fun createArticle(userId: Long,req: ArticleReq.Create) : Long {
        val command = req.toCommand()
        val user = userRepository.getReferenceById(userId)
        val article = Article.create(command = command, author = user)
        articleRepository.save(article)
        return article.id!!
    }

    @Transactional
    fun updateArticle(userId:Long, articleId: Long, req: ArticleReq.Update) {
        val command = req.toCommand()
        val article = articleRepository.findByIdOrThrow(articleId)
        if(article.author.id != userId) {
            throw IllegalArgumentException("작성자만 수정할 수 있습니다.")
        }
        article.update(command)
    }

    @Transactional(readOnly = true)
    fun getArticlePaging(req: PagingReq, sort: ArticleCommand.PagingSort): List<ArticleRes.ArticleDto> {
        val articlePage = articleQueryRepository.getArticlePaging(req.toPageable(), sort)
        return articlePage.map(ArticleRes.ArticleDto::from)
    }

    /**
     * 게시글 상세 조회
     * 1. 조회수 증가
     * 2. 게시글 조회
     */
    @Transactional
    fun getArticleDetail(articleId: Long): ArticleRes.ArticleDetailDto {
        articleRepository.addViewsCountById(articleId)

        val article = articleRepository.findByIdOrThrow(articleId)
        val agreesCount = articleAgreeRepository.countByArticleId(articleId)
        val disagreesCount = articleDisagreeRepository.countByArticleId(articleId)
        return ArticleRes.ArticleDetailDto.from(article, agreesCount, disagreesCount)
    }

    @Transactional
    fun agreeArticle(userId: Long, articleId: Long): Long {
        val user = userRepository.getReferenceById(userId)
        val article = articleRepository.getReferenceById(articleId)
        if(articleAgreeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw IllegalArgumentException("이미 추천한 게시글입니다.")
        }
        if(articleDisagreeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw IllegalArgumentException("반대한 게시글은 추천할 수 없습니다.")
        }

        val agree = ArticleAgree(article = article, user = user)

        articleAgreeRepository.save(agree)
        return articleAgreeRepository.countByArticleId(articleId)
    }

    @Transactional
    fun disagreeArticle(userId: Long, articleId: Long): Long {
        val user = userRepository.getReferenceById(userId)
        val article = articleRepository.getReferenceById(articleId)
        if(articleDisagreeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw IllegalArgumentException("이미 반대한 게시글입니다.")
        }
        if(articleAgreeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw IllegalArgumentException("추천한 게시글은 반대할 수 없습니다.")
        }

        val disAgree = ArticleDisagree(article = article, user = user)

        articleDisagreeRepository.save(disAgree)
        return articleDisagreeRepository.countByArticleId(articleId)
    }
}