package com.whoiswoony.springtutorial.domain.member

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Authority (
    val name: String,

    @JoinColumn(name="member")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var member:Member? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id:Long? = null
)