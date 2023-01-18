package com.whoiswoony.springtutorial.service.quizset

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.quizset.*
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import com.whoiswoony.springtutorial.dto.QuizSetResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizSetService (private val quizSetRepository: QuizSetRepository,
                      private val quizSetRepositorySupport: QuizSetRepositorySupport,
                      private val memberRepository: MemberRepository,
                      private val quizSetViewRepository: QuizSetViewRepository){

    fun addQuizSet(memberEmail:String, addQuizSetRequest: AddQuizSetRequest){
        val member = memberRepository.findByEmail(memberEmail)!!
        val quizSet = QuizSet(addQuizSetRequest.title, addQuizSetRequest.description, member)
        quizSetRepository.save(quizSet)
    }
    fun getQuizSet(): MutableList<QuizSetResponse> {
        return quizSetRepositorySupport.findTop10()
    }

    fun addQuizSetView(quizSetId:Long, ipAddress:String){
        val quizSet = quizSetRepository.findByIdOrNull(quizSetId)
        quizSet ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ_SET)

        val quizSetView = QuizSetView(quizSet, ipAddress)
        quizSetViewRepository.save(quizSetView)
    }

    fun getMyQuizSet(memberEmail: String) : MutableList<QuizSet>? {
        val member = memberRepository.findByEmail(memberEmail)!!
        return member.id?.let { quizSetRepository.findQuizSetByMemberId(it) }
    }
}