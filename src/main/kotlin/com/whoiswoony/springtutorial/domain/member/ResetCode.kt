package com.whoiswoony.springtutorial.domain.member

import org.hibernate.annotations.ColumnDefault
import java.util.*
import javax.persistence.*

@Entity(name="RESET_CODE")
class ResetCode (
    var code: String ?= null,

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