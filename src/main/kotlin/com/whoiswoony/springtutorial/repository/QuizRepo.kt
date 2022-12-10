package com.whoiswoony.springtutorial.repository

import com.whoiswoony.springtutorial.controller.QuizApi
import com.whoiswoony.springtutorial.model.QuizType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepo: JpaRepository<QuizType, String>