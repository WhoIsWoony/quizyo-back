package com.whoiswoony.springtutorial.domain.group

import com.whoiswoony.springtutorial.dto.group.GroupResponse
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "groups")
class Group(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0,
    var title: String,
    var description: String,
){
    fun toResponse() = GroupResponse(
        title = title,
        description = description
    )
}