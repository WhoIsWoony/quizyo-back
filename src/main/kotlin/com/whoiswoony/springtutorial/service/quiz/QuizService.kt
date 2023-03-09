package com.whoiswoony.springtutorial.service.quiz

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.BucketRepository
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import com.whoiswoony.springtutorial.domain.quiz.QuizRepository
import com.whoiswoony.springtutorial.dto.quiz.*
import com.whoiswoony.springtutorial.logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizService (private val quizRepository: QuizRepository, private val bucketRepository: BucketRepository){
    fun addQuiz(addQuizRequest: AddQuizRequest): AddQuizResponse {
        val bucket = bucketRepository.findByIdOrNull(addQuizRequest.bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val quiz = Quiz(addQuizRequest.question, addQuizRequest.answer, addQuizRequest.sequence, bucket)
        val result = quizRepository.save(quiz)
        return AddQuizResponse(result.id!!)
    }

    fun deleteQuiz(deleteQuizRequest: DeleteQuizRequest): Boolean {
        quizRepository.deleteById(deleteQuizRequest.id)
        return true
    }

    fun changeQuizOrder(changeQuizOrder: ChangeQuizOrder){
        val quiz = quizRepository.findByIdOrNull(changeQuizOrder.quizId)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.sequence = changeQuizOrder.sequence
        quizRepository.save(quiz)
    }


    fun updateQuiz(updateQuizRequest: UpdateQuizRequest): UpdateQuizResponse {
        val quiz = quizRepository.findByIdOrNull(updateQuizRequest.id)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.question = updateQuizRequest.question
        quiz.answer = updateQuizRequest.answer
        quiz.sequence = updateQuizRequest.sequence

        val result = quizRepository.save(quiz)
        return UpdateQuizResponse(result.id!!)
    }

    fun getQuiz(bucketId:Long): GetQuizResponse {
        val quiz = quizRepository.findByBucketId(bucketId)
        val response = quiz.map{QuizDto(it.id!!, it.question, it.answer, it.sequence)}.toMutableList()
        return GetQuizResponse(response)
    }
}