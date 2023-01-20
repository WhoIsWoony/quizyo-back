package com.whoiswoony.springtutorial.domain.quizset

import com.querydsl.jpa.impl.JPAQueryFactory
import com.whoiswoony.springtutorial.domain.quizset.QQuizSetView.quizSetView
import com.whoiswoony.springtutorial.dto.QuizSetResponse
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class QuizSetRepositorySupport(private val jpaQueryFactory:JPAQueryFactory):QuerydslRepositorySupport(QuizSet::class.java){
    fun findTop10(): MutableList<QuizSetResponse> {
        //퀴즈셋 조회수 카운트 서브쿼리
        val result = jpaQueryFactory
            .selectFrom(quizSetView)
            .groupBy(quizSetView.quizSet)
            .orderBy(quizSetView.quizSet.count().desc())
            .limit(10)
            .fetch()

        return result.map{
            QuizSetResponse(
                it.quizSet.title,
                it.quizSet.description,
                it.quizSet.sharedQuizSets.size ,
                it.quizSet.views.size,
                it.quizSet.id
            )}.toMutableList()
    }
}