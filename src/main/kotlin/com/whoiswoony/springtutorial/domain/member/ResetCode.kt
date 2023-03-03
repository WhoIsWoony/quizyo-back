package com.whoiswoony.springtutorial.domain.member

import java.util.*
import javax.persistence.*

@Entity(name="RESET_CODE")
class ResetCode (
    @Column(unique = true)
    var code: String,

    @Temporal(TemporalType.TIME)
    var expireTime: Date? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    var member: Member? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,
)