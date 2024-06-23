package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.BaseTimeEntity
import com.reditus.agreeassociation.domain.user.User
import jakarta.persistence.*

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 255)
    var title: String,
    @Column(nullable = false, length = 16000)
    var content: String,

    @Enumerated(EnumType.STRING)
    var articleStatus: ArticleStatus = ArticleStatus.PENDING,
    @Column(nullable = false)
    var viewsCount: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "author_id")
    val author: User,
) : BaseTimeEntity() {
    fun update(command: ArticleCommand.Update) {
        this.title = command.title
        this.content = command.content
    }

    companion object {
        fun create(command: ArticleCommand.Create, author: User): Article {
            return Article(
                title = command.title,
                content = command.content,
                author = author,
                articleStatus = ArticleStatus.PENDING,
                viewsCount = 0
            )
        }
    }
}
