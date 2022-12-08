package com.whoiswoony.springtutorial.repository

import com.whoiswoony.springtutorial.model.SpaceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpaceRepo:JpaRepository<SpaceType, String>