package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.deck.DeckRequest
import com.whoiswoony.springtutorial.dto.deck.DeckResponse
import com.whoiswoony.springtutorial.service.deck.DeckService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name="덱", description = "덱 관련 api 입니다.")
@RestController
@RequestMapping("/api/v1")
class DeckController(private val deckService: DeckService){
    @Operation(summary = "덱 생성", description = "덱을 생성합니다.")
    @PostMapping("/saveDeck")
    fun saveDeck(@RequestParam deckRequest: DeckRequest): DeckResponse {
        var deck = deckRequest.toEntity()
        deck = deckService.saveDeck(deck)
        return deck.toResponse()
    }

    @Operation(summary = "덱 전부 가져오기", description = "덱을 전부 가져옵니다.")
    @GetMapping("/getAllDecks")
    fun getAllDecks():List<DeckResponse>{
        val allDecks = deckService.getAllDecks()
        return allDecks.map { it.toResponse() }
    }
}