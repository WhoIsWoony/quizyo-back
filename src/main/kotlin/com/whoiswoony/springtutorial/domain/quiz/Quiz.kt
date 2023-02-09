package com.whoiswoony.springtutorial.domain.quiz

import com.fasterxml.jackson.annotation.JsonIgnore
import com.whoiswoony.springtutorial.domain.bucket.Bucket
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMy
import javax.persistence.*

@Entity(name="QUIZ")
class Quiz(
    var question: String,

    var answer: String,

    var sequence: Long,


    @JoinColumn
    @ManyToOne
    @JsonIgnore
    var bucket: Bucket,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)