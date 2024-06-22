package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.BaseTimeEntity
import com.reditus.agreeassociation.domain.user.User
import jakarta.persistence.*

@Entity
class ArticleComment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var content: String,
    val parentPath: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "author_id")
    val author: User,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "article_id")
    val article: Article,
) : BaseTimeEntity() {
    companion object {
        fun create(
            command: ArticleCommentCommand.Create,
            author: User,
            article: Article,
        ): ArticleComment {
            return ArticleComment(
                content = command.content,
                parentPath = command.parentPath,
                author = author,
                article = article
            )
        }
    }

}