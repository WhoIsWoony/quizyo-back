package com.whoiswoony.springtutorial.domain.member

import com.whoiswoony.springtutorial.domain.bucket.Bucket
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMy
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity(name="MEMBER")
@DynamicUpdate
class Member(
    @Column(unique = true)
    val email: String,

    var password: String,

    @Column(unique = true)
    val nickname: String,

    @OneToOne(mappedBy = "member", cascade = [CascadeType.PERSIST])
    val resetCode: ResetCode,

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