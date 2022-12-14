package com.whoiswoony.springtutorial.dto.group

import com.whoiswoony.springtutorial.domain.group.Group

data class GroupRequest (
    var title:String,
    var description:String
){
    fun toEntity() = Group(
        title = title,
        description = description
    )
}