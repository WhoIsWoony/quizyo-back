package com.whoiswoony.springtutorial.domain.deck

import com.whoiswoony.springtutorial.dto.deck.DeckResponse
import javax.persistence.*

@Entity
@Table(name="decks")
class Deck(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0,
    var title: String
){
    fun toResponse() = DeckResponse(id, title)
}