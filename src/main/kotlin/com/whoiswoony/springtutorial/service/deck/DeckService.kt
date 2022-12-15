package com.whoiswoony.springtutorial.service.deck

import com.whoiswoony.springtutorial.domain.deck.Deck
import com.whoiswoony.springtutorial.domain.deck.DeckRepository
import org.springframework.stereotype.Service

@Service
class DeckService(private val deckRepository: DeckRepository) {

    fun saveDeck(deck: Deck): Deck {
        return deckRepository.save(deck)
    }

    fun getAllDecks():List<Deck>{
        return deckRepository.findAll()
    }
}