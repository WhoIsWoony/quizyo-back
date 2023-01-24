package com.whoiswoony.springtutorial.service.bucket

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.bucket.*
import com.whoiswoony.springtutorial.dto.bucket.AddBucketRequest
import com.whoiswoony.springtutorial.dto.bucket.BucketTop10Response
import com.whoiswoony.springtutorial.dto.bucket.BucketResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BucketService (private val bucketRepository: BucketRepository,
                     private val bucketRepositorySupport: BucketRepositorySupport,
                     private val memberRepository: MemberRepository,
                     private val bucketViewRepository: BucketViewRepository){

    fun addBucket(memberEmail:String, addBucketRequest: AddBucketRequest){
        val member = memberRepository.findByEmail(memberEmail)!!
        val bucket = Bucket(addBucketRequest.title, addBucketRequest.description, member)
        bucketRepository.save(bucket)
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


    fun getFindTop10(): BucketTop10Response {
        val buckets = bucketRepositorySupport.findTop10()
        return BucketTop10Response(buckets)
    }

    fun addBucketView(bucketId:Long, ipAddress:String){
        val bucket = bucketRepository.findByIdOrNull(bucketId)
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val bucketView = BucketView(bucket, ipAddress)
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
}