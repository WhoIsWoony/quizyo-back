package com.whoiswoony.springtutorial.domain.user

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="users")
class User (
    val uid: String,
    val upw: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
)