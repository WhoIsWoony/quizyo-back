package com.whoiswoony.springtutorial.repository

import com.whoiswoony.springtutorial.model.SetType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SetRepo:JpaRepository<SetType, String>