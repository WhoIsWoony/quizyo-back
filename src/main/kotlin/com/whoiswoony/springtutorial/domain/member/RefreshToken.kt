package com.whoiswoony.springtutorial.domain.member

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*;

@Entity
class RefreshToken(

        @JoinColumn(name = "member")
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        var member: Member,

        @Column(unique = true)
        var refreshToken: String,


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
)
