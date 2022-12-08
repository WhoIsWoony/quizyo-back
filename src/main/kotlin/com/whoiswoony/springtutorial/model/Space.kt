package com.whoiswoony.springtutorial.model

import javax.persistence.*

@Entity
@Table(name="SPACE")
class Space(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val title:String,
    val content:String,
    val private:Boolean,

    @OneToMany
    @JoinColumn(name="set_id")
    var sets: MutableList<Set> = ArrayList(),
)