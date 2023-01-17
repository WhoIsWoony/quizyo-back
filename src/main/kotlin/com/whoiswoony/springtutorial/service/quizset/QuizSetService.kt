package com.whoiswoony.springtutorial.service.quizset

import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.quizset.QuizSet
import com.whoiswoony.springtutorial.domain.quizset.QuizSetRepository
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import org.springframework.stereotype.Service

@Service
class QuizSetService (private val quizSetRepository: QuizSetRepository, private val memberRepository: MemberRepository){

    fun addQuizSet(memberEmail:String, addQuizSetRequest: AddQuizSetRequest){
        val member = memberRepository.findByEmail(memberEmail)!!
        val quizSet = QuizSet(addQuizSetRequest.title, addQuizSetRequest.description, member)
        quizSetRepository.save(quizSet)
    }
    fun getQuizSet(): MutableList<QuizSet> {
        return quizSetRepository.findAll()
    }
}