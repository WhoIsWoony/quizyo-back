package com.whoiswoony.springtutorial.service.quizset

import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.quizset.QuizSet
import com.whoiswoony.springtutorial.domain.quizset.QuizSetRepository
import com.whoiswoony.springtutorial.dto.AddQuizSetRequest
import com.whoiswoony.springtutorial.dto.MyOwnQuizSetResponse
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

    fun myOwnQuizSet(memberEmail: String): MutableList<MyOwnQuizSetResponse>? {
        val member = memberRepository.findByEmail(memberEmail)!!
        val myOwnQuizSets = member.id?.let { quizSetRepository.findQuizSetByMemberId(it) }
        val quizSetsFromMyOwnQuizSets: MutableList<MyOwnQuizSetResponse> = mutableListOf()

        if (myOwnQuizSets != null) {
            for (ownQuizSet in myOwnQuizSets) {
                val myOwnQuizSet = MyOwnQuizSetResponse(
                    ownQuizSet.title,
                    ownQuizSet.description,
                    ownQuizSet.sharedQuizSets.count(),
                    ownQuizSet.id
                )
                quizSetsFromMyOwnQuizSets.add(myOwnQuizSet)
            }
        }
        return quizSetsFromMyOwnQuizSets
    }
}