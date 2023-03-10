package com.whoiswoony.springtutorial.domain.bucket

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.core.annotations.QueryProjection
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import javax.persistence.*

@Entity(name="BUCKET")
class Bucket @QueryProjection constructor(
    val title:String,

    val description:String,

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    val member: Member,

    @OneToMany(mappedBy = "bucket")
    val views:MutableList<BucketView> = mutableListOf(),

    @OneToMany(mappedBy = "bucket", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val bucketShares: MutableList<BucketShareMy> = mutableListOf(),

    @OneToMany(mappedBy = "bucket", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val quizs: MutableList<Quiz> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)