package com.whoiswoony.springtutorial.repository

import com.whoiswoony.springtutorial.model.UserType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo:JpaRepository<UserType, String>