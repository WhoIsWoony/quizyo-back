package com.whoiswoony.springtutorial.domain.member

import java.util.Date
import javax.persistence.*

@Entity(name="VERIFICATION")
class Verification (
    @Column(unique = true)
    val email: String,

    val code: String,

    @Temporal(TemporalType.DATE)
    var expireTime: Date = Date(),
){
    @PrePersist
    private fun setExpireTime() { expireTime = Date() }
}