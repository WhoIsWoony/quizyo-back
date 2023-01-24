package com.whoiswoony.springtutorial.service.bucket

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.bucket.BucketRepository
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMy
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMyRepository
import com.whoiswoony.springtutorial.dto.bucket.AddBucketShareMyRequest
import com.whoiswoony.springtutorial.dto.bucket.BucketResponse
import org.springframework.stereotype.Service

@Service
class BucketShareMyService (
    private val bucketShareMyRepository: BucketShareMyRepository,
    private val bucketRepository: BucketRepository,
    private val memberRepository: MemberRepository
) {
    fun addBucketShareMy(memberEmail: String, addBucketShareMyRequest: AddBucketShareMyRequest) {
        val member = memberRepository.findByEmail(memberEmail)!!
        val bucket = bucketRepository.findById(addBucketShareMyRequest.bucketId).orElse(null)

        //Bucket set 존재 X시, 오류 발생
        bucket ?: throw CustomException(ErrorCode.NOT_FOUND_BUCKET)

        val bucketShareMy = BucketShareMy(bucket, member)

        bucketShareMyRepository.save(bucketShareMy)
    }

    fun getBucketShareMy(memberEmail : String): MutableList<BucketResponse>? {
        val member = memberRepository.findByEmail(memberEmail)!!
        val bucketShare = member.bucketShares
        val result: MutableList<BucketResponse> = mutableListOf()

        for (bucket in bucketShare){
            val bucketResponse = BucketResponse(
                title = bucket.bucket.title,
                description = bucket.bucket.description,
                shareNum = bucket.bucket.bucketShares.count(),
                viewNum = bucket.bucket.views.count(),
                id = bucket.bucket.id!!
            )
            result.add(bucketResponse)
        }
        return result
    }
}