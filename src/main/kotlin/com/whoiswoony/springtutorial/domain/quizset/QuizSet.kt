package com.whoiswoony.springtutorial.domain.quizset

import com.fasterxml.jackson.annotation.JsonIgnore
import com.whoiswoony.springtutorial.domain.member.Member
import javax.persistence.*

@Entity
class QuizSet (
    val title:String,

    val description:String,

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)