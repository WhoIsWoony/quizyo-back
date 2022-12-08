package com.whoiswoony.springtutorial.model

import javax.persistence.*
import kotlin.collections.Set

@Entity
@Table(name="SET")
class Set (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val title:String,

    @OneToMany
    @JoinColumn(name="quiz_id")
    var quizs: MutableList<Quiz> = ArrayList(),
)