package com.whoiswoony.springtutorial.domain.member

import com.whoiswoony.springtutorial.domain.quizset.QuizSet
import javax.persistence.*

@Entity
class Member(
    @Column(unique = true)
    val email: String,

    val password: String,

    @Column(unique = true)
    val nickname: String,

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var roles: MutableSet<Authority> = mutableSetOf(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val refreshTokens: MutableList<RefreshToken> = arrayListOf(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val quizSets: MutableList<QuizSet> = arrayListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)