package com.whoiswoony.springtutorial.domain.deck

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeckRepository:JpaRepository<Deck, Long>