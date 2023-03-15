package com.whoiswoony.springtutorial.service.bucket

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.*
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.dto.bucket.*
import com.whoiswoony.springtutorial.service.Validation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate

@Service
class BucketService (private val bucketRepository: BucketRepository,
                     private val bucketRepositorySupport: BucketRepositorySupport,
                     private val memberRepository: MemberRepository,
                     private val bucketViewRepository: BucketViewRepository,
                     private val validation: Validation){

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

    fun addBucketView(addBucketViewRequest: AddBucketViewRequest){
        val bucket = bucketRepository.findByIdOrNull(addBucketViewRequest.bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val member = memberRepository.findByEmail(addBucketViewRequest.email)
        member ?: throw CustomException(ErrorCode.NOT_EXIST_MEMBER)

        if(!checkBucketViewTimeConstraint(bucket.views, addBucketViewRequest.email))
            throw CustomException(ErrorCode.INVALID_BUCKET_VIEW_UPDATE_TIME)

        val bucketView = BucketView(bucket, addBucketViewRequest.email)
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