package com.whoiswoony.springtutorial.domain.quiz

import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository:JpaRepository<Quiz, Long>