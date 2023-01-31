package com.whoiswoony.springtutorial.domain.bucket

import com.fasterxml.jackson.annotation.JsonIgnore
import com.whoiswoony.springtutorial.domain.member.Member
import javax.persistence.*

@Entity
class BucketShareMy(
    @JoinColumn(name = "bucketShares", unique = true)
    @ManyToOne
    @JsonIgnore
    val bucket: Bucket,

    @JoinColumn
    @ManyToOne
    @JsonIgnore
    val member: Member,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)
