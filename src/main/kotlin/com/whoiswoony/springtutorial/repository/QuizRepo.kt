package com.whoiswoony.springtutorial.repository

import com.whoiswoony.springtutorial.controller.QuizApi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepo: JpaRepository<QuizApi, String>