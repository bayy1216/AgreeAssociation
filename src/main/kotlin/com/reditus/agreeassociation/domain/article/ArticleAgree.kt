package com.reditus.agreeassociation.domain.article

import com.reditus.agreeassociation.domain.user.User
import jakarta.persistence.*

@Entity
class ArticleAgree(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id")
    val article: Article,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User
){

}