package com.whoiswoony.springtutorial.domain.member

import com.whoiswoony.springtutorial.domain.bucket.Bucket
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMy
import javax.persistence.*

@Entity
class Member(
    @Column(unique = true)
    val email: String,

    val password: String,

    @Column(unique = true)
    val nickname: String,

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var roles: MutableSet<Authority> = mutableSetOf(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val refreshTokens: MutableList<RefreshToken> = arrayListOf(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val buckets: MutableList<Bucket> = arrayListOf(),

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val bucketShares: MutableList<BucketShareMy> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)