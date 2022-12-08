package com.whoiswoony.springtutorial

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "todo")
class TodoModel (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val title: String,
    val content: String,
    val updatedDate: LocalDateTime,
    val createdDate: LocalDateTime,
){
    fun toDto() = TodoDTO(
            id = this.id,
            title = this.title,
            content = this.content
    )
}
