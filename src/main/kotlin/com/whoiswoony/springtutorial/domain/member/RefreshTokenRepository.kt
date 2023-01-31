package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository:JpaRepository<RefreshToken, Long> {
    fun findByRefreshToken(refreshToken: String):RefreshToken?
}
