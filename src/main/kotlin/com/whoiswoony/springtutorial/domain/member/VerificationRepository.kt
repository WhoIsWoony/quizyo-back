package com.whoiswoony.springtutorial.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface VerificationRepository:JpaRepository<Verification, String>