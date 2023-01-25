package com.whoiswoony.springtutorial.service.quiz

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.BucketRepository
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import com.whoiswoony.springtutorial.domain.quiz.QuizRepository
import com.whoiswoony.springtutorial.dto.quiz.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizService (private val quizRepository: QuizRepository, private val bucketRepository: BucketRepository){
    fun addQuiz(addQuizRequest: AddQuizRequest){
        val bucket = bucketRepository.findByIdOrNull(addQuizRequest.bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val quiz = Quiz(addQuizRequest.question, addQuizRequest.answer, addQuizRequest.sequence, bucket)
        quizRepository.save(quiz)
    }

    fun deleteQuiz(deleteQuizRequest: DeleteQuizRequest){
        quizRepository.deleteById(deleteQuizRequest.id)
    }

    fun changeQuizOrder(changeQuizOrder: ChangeQuizOrder){
        val quiz = quizRepository.findByIdOrNull(changeQuizOrder.quizId)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.sequence = changeQuizOrder.sequence
        quizRepository.save(quiz)
    }


    fun updateQuiz(updateQuizRequest: UpdateQuizRequest){
        val quiz = quizRepository.findByIdOrNull(updateQuizRequest.id)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.question = updateQuizRequest.question
        quiz.answer = updateQuizRequest.answer
        quiz.sequence = updateQuizRequest.sequence

        quizRepository.save(quiz)
    }

    fun getQuiz(bucketId:Long): GetQuizResponse {
        val quiz = quizRepository.findByBucketId(bucketId)
        val response = quiz.map{QuizDto(it.id!!, it.question, it.answer, it.sequence)}.toMutableList()
        return GetQuizResponse(response)
    }
}