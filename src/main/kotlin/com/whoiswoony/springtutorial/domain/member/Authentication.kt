package com.whoiswoony.springtutorial.domain.member

import java.util.Date
import javax.persistence.*

@Entity(name="AUTHENTICATION")
class Authentication (
    val email: String,

    var code: String,

    //type = REGISTER, PASSWORD_CHANGE...
    var type: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @Temporal(TemporalType.TIME)
    var createdTime: Date = Date(),

){
    @PrePersist
    private fun setCreatedTime() { createdTime = Date() }
}