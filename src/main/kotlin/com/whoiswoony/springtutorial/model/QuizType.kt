package com.whoiswoony.springtutorial.model

import javax.persistence.*

@Entity
@Table(name="SET")
class QuizType (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val question:String,
    val answer:String
)