package com.whoiswoony.springtutorial.domain.quizset

import com.fasterxml.jackson.annotation.JsonIgnore
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.domain.member.RefreshToken
import javax.persistence.*

@Entity
class QuizSet (
    val title:String,

    val description:String,

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    val member: Member,

    @OneToMany(mappedBy = "quizSet")
    val views:MutableList<QuizSetView> = mutableListOf(),

    @OneToMany(mappedBy = "quizSet", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val sharedQuizSets: MutableList<SharedQuizSet> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)