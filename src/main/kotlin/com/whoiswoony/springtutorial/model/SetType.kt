package com.whoiswoony.springtutorial.model

import javax.persistence.*

@Entity
@Table(name="SET")
class SetType (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val title:String,

    @OneToMany
    @JoinColumn(name="quiz_id")
    var quizs: MutableList<QuizType> = ArrayList(),
)