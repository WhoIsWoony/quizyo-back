package com.whoiswoony.springtutorial.domain.quizset

import com.fasterxml.jackson.annotation.JsonIgnore
import com.whoiswoony.springtutorial.domain.member.Member
import java.util.*
import javax.persistence.*

@Entity
class SharedQuizSet(
    @JoinColumn(name = "quizSet", unique = true)
    @ManyToOne
    @JsonIgnore
    val quizSet: QuizSet,

    @JoinColumn(name = "member")
    @ManyToOne
    @JsonIgnore
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)
