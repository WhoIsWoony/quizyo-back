package com.whoiswoony.springtutorial.domain.quizset

import com.querydsl.jpa.impl.JPAQueryFactory
import com.whoiswoony.springtutorial.domain.quizset.QQuizSet.quizSet
import com.whoiswoony.springtutorial.domain.quizset.QQuizSetView.quizSetView
import com.whoiswoony.springtutorial.dto.QQuizSetResponse
import com.whoiswoony.springtutorial.dto.QuizSetResponse
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class QuizSetRepositorySupport(private val jpaQueryFactory:JPAQueryFactory):QuerydslRepositorySupport(QuizSet::class.java){
    fun findTop10(): MutableList<QuizSetResponse> {
        //퀴즈셋 조회수 카운트 서브쿼리
        return jpaQueryFactory
            .select(QQuizSetResponse(quizSet.title, quizSet.description, quizSetView.count(), quizSet.id))
            .from(quizSetView)
            .groupBy(quizSetView.quizSet)
            .rightJoin(quizSetView.quizSet, quizSet)
            .orderBy(quizSet.count().desc())
            .limit(10)
            .fetch()
    }
}