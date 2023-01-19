package com.whoiswoony.springtutorial.service.quizset

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.quizset.QuizSetRepository
import com.whoiswoony.springtutorial.domain.quizset.QuizSetViewRepository
import com.whoiswoony.springtutorial.domain.quizset.SharedQuizSet
import com.whoiswoony.springtutorial.domain.quizset.SharedQuizSetRepository
import com.whoiswoony.springtutorial.dto.AddSharedQuizSetRequest
import com.whoiswoony.springtutorial.dto.QuizSetResponse
import org.springframework.stereotype.Service

@Service
class SharedQuizSetService (
    private val sharedQuizSetRepository: SharedQuizSetRepository,
    private val quizSetRepository: QuizSetRepository,
    private val quizSetViewRepository: QuizSetViewRepository,
    private val memberRepository: MemberRepository
) {
    fun addSharedQuizSet(memberEmail: String, addSharedQuizSetRequest: AddSharedQuizSetRequest) {
        val member = memberRepository.findByEmail(memberEmail)!!
        val quizSet = quizSetRepository.findById(addSharedQuizSetRequest.quizSetId).orElse(null)

        //Quiz set 존재 X시, 오류 발생
        quizSet ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ_SET)

        val sharedQuizSet = SharedQuizSet(quizSet, member)

        sharedQuizSetRepository.save(sharedQuizSet)
    }

    fun mySharedQuizSet(memberEmail : String): MutableList<QuizSetResponse>? {
        val member = memberRepository.findByEmail(memberEmail)!!
        val mySharedQuizSets = member.sharedQuizSets
        val quizSetsFromMySharedQuizSets: MutableList<QuizSetResponse> = mutableListOf()

        if (mySharedQuizSets != null) {
            for (sharedQuizSet in mySharedQuizSets){
                val mySharedQuizSet = QuizSetResponse(
                    title = sharedQuizSet.quizSet.title,
                    description = sharedQuizSet.quizSet.description,
                    shareNum = sharedQuizSet.quizSet.sharedQuizSets.count().toLong(),
                    viewNum = sharedQuizSet.quizSet.views.count().toLong(),
                    id = sharedQuizSet.quizSet.id!!
                )
                quizSetsFromMySharedQuizSets.add(mySharedQuizSet)
            }
        }
        return quizSetsFromMySharedQuizSets
    }
}