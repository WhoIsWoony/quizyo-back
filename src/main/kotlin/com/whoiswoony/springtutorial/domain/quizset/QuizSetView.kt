package com.whoiswoony.springtutorial.domain.quizset

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.Date
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames =  ["QUIZSET_ID", "ipAddress", "createdDate"])])
class QuizSetView (
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QUIZSET_ID")
    val quizSet:QuizSet,

    val ipAddress:String,

    @Temporal(TemporalType.DATE)
    var createdDate:Date = Date(),

    @Temporal(TemporalType.TIME)
    var createdTime:Date = Date(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
){
    @PrePersist
    private fun setCreatedDate(){
        createdDate = Date()
        createdTime = Date()
    }
}