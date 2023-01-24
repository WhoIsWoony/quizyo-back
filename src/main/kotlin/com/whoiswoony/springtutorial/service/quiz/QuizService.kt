package com.whoiswoony.springtutorial.service.quiz

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.BucketRepository
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import com.whoiswoony.springtutorial.domain.quiz.QuizRepository
import com.whoiswoony.springtutorial.dto.quiz.AddQuizRequest
import com.whoiswoony.springtutorial.dto.quiz.ChangeQuizOrder
import com.whoiswoony.springtutorial.dto.quiz.DeleteQuizRequest
import com.whoiswoony.springtutorial.dto.quiz.UpdateQuizRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class QuizService (private val quizRepository: QuizRepository, private val bucketRepository: BucketRepository){
    fun addQuiz(addQuizRequest: AddQuizRequest){
        val bucket = bucketRepository.findByIdOrNull(addQuizRequest.bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val quiz = Quiz(addQuizRequest.question, addQuizRequest.answer, addQuizRequest.order, bucket)
        quizRepository.save(quiz)
    }

    fun deleteQuiz(deleteQuizRequest: DeleteQuizRequest){
        quizRepository.deleteById(deleteQuizRequest.quizId)
    }

    fun changeQuizOrder(changeQuizOrder: ChangeQuizOrder){
        val quiz = quizRepository.findByIdOrNull(changeQuizOrder.quizId)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.order = changeQuizOrder.order
        quizRepository.save(quiz)
    }


    fun updateQuiz(updateQuizRequest: UpdateQuizRequest){
        val quiz = quizRepository.findByIdOrNull(updateQuizRequest.quizId)
        quiz ?: throw CustomException(ErrorCode.NOT_FOUND_QUIZ)

        quiz.question = updateQuizRequest.question
        quiz.answer = updateQuizRequest.answer
        quiz.order = updateQuizRequest.order

        quizRepository.save(quiz)
    }
}