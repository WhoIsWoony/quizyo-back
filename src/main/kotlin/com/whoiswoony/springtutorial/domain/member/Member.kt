package com.whoiswoony.springtutorial.domain.member

import com.fasterxml.jackson.annotation.JsonIgnore
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
    val refreshToken: MutableList<RefreshToken> = arrayListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)