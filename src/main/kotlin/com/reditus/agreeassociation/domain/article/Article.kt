package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.BaseTimeEntity
import com.reditus.agreeassociation.domain.user.User
import jakarta.persistence.*

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var content: String,

    @Enumerated(EnumType.STRING)
    var articleStatus: ArticleStatus = ArticleStatus.NOTHING,

    var viewsCount: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "author_id")
    val author: User,
) : BaseTimeEntity() {
    companion object {
        fun create(command: ArticleCommand.Create, author: User): Article {
            return Article(
                title = command.title,
                content = command.content,
                author = author,
                articleStatus = ArticleStatus.NOTHING,
                viewsCount = 0
            )
        }
    }
}
