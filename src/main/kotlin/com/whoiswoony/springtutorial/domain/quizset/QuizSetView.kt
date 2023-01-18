package com.whoiswoony.springtutorial.domain.quizset

import java.util.Date
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames =  ["QUIZSET_ID", "ipAddress", "createdDate"])])
class QuizSetView (
    @ManyToOne
    @JoinColumn(name = "QUIZSET_ID")
    val quizSet:QuizSet,

    val ipAddress:String,

    @Temporal(TemporalType.DATE)
    val createdDate:Date,

    @Temporal(TemporalType.TIME)
    val createdTime:Date,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)