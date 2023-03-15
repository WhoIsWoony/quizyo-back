package com.whoiswoony.springtutorial.service.bucket

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.*
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.dto.bucket.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@Service
class BucketService (private val bucketRepository: BucketRepository,
                     private val bucketRepositorySupport: BucketRepositorySupport,
                     private val memberRepository: MemberRepository,
                     private val bucketViewRepository: BucketViewRepository,
                     private val jwtUtils: JwtUtils,){

    fun addBucket(memberEmail:String, addBucketRequest: AddBucketRequest): Long? {
        val member = memberRepository.findByEmail(memberEmail)!!
        val bucket = Bucket(addBucketRequest.title, addBucketRequest.description, member)
        val result = bucketRepository.save(bucket)
        return result.id
    }

    fun getBucket(): MutableList<BucketResponse> {
        val buckets = bucketRepository.findAll()
        val result: MutableList<BucketResponse> = mutableListOf()

        for (bucket in buckets) {
            val bucketResponse = BucketResponse(
                title = bucket.title,
                description = bucket.description,
                shareNum = bucket.bucketShares.count(),
                viewNum = bucket.views.count(),
                id = bucket.id!!
            )
            result.add(bucketResponse)
        }

        return result
    }


    fun getOneBucket(bucketId:Long): BucketResponse {
        val bucket = bucketRepository.findByIdOrNull(bucketId)

        bucket?:throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        return BucketResponse(
            bucket.title,
            bucket.description,
            bucket.bucketShares.count(),
            bucket.views.count(),
            bucket.id!!
        )
    }


    fun checkMine(bucketId:Long, memberEmail:String): BucketCheckMine {
        val member = memberRepository.findByEmail(memberEmail)!!
        val findBucket = member.buckets.find { it.id == bucketId }
        return BucketCheckMine(findBucket!=null)
    }


    fun getFindTop10(): BucketTop10Response {
        val buckets = bucketRepositorySupport.findTop10()
        return BucketTop10Response(buckets)
    }

    fun addBucketView(bucketId: Long, request: HttpServletRequest){
        val bucket = bucketRepository.findByIdOrNull(bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val refreshToken = jwtUtils.resolveToken(request)
        refreshToken ?: throw CustomException(ErrorCode.INVALID_TOKEN)

        val email = jwtUtils.getAuthentication(refreshToken).name

        if(!checkBucketViewTimeConstraint(bucket.views, email))
            throw CustomException(ErrorCode.INVALID_BUCKET_VIEW_UPDATE_TIME)

        val bucketView = BucketView(bucket, email)
        bucketViewRepository.save(bucketView)
    }

    fun getBucketMy(memberEmail: String): MutableList<BucketResponse>? {
        val member = memberRepository.findByEmail(memberEmail)!!
        val buckets = member.buckets
        val result: MutableList<BucketResponse> = mutableListOf()

        for (bucket in buckets) {
            val bucketResponse = BucketResponse(
                title = bucket.title,
                description = bucket.description,
                shareNum = bucket.bucketShares.count(),
                viewNum = bucket.views.count(),
                id = bucket.id!!
            )
            result.add(bucketResponse)
        }
        return result
    }

    fun checkBucketViewTimeConstraint(bucketViewList: MutableList<BucketView>, email: String): Boolean {
        val date = Date.valueOf(LocalDate.now())

        for (bucketView in bucketViewList)
            if(date == bucketView.createdDate && bucketView.email == email)
                return false

        return true
    }
}