package com.whoiswoony.springtutorial.dto.deck

import com.whoiswoony.springtutorial.domain.deck.Deck

data class DeckRequest(val title:String){
    fun toEntity() = Deck(title = title)
}