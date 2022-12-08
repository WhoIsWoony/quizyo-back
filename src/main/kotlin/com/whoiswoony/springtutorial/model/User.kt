package com.whoiswoony.springtutorial.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="USER")
class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val email: String,
    val password: String,
    val nickname: LocalDateTime,
    val profile: LocalDateTime
)