package com.whoiswoony.springtutorial.domain.member

import java.util.Date
import javax.persistence.*

@Entity(name="AUTHENTICATION")
class Authentication (
    val email: String,

    @Column(unique = true)
    var code: String,

    @Temporal(TemporalType.TIME)
    var expireTime: Date,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
)