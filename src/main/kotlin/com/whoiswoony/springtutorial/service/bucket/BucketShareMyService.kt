package com.whoiswoony.springtutorial.service.bucket

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.Bucket
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.bucket.BucketRepository
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMy
import com.whoiswoony.springtutorial.domain.bucket.BucketShareMyRepository
import com.whoiswoony.springtutorial.dto.bucket.AddBucketShareMyRequest
import com.whoiswoony.springtutorial.dto.bucket.BucketResponse
import org.springframework.stereotype.Service
import javax.swing.text.StyledEditorKit.BoldAction

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

        //자신이 생성한 버킷은 퍼오기 불가능
        if(bucket.member.email == memberEmail)
            throw CustomException(ErrorCode.INVALID_BUCKET_SHARE)

        val bucketShareMy = BucketShareMy(bucket, member)

        if(checkDuplicateBucketShareMy(member.bucketShares, memberEmail, bucket))
            throw CustomException(ErrorCode.DUPLICATE_BUCKET_SHARE_MY)
        else
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

    fun checkDuplicateBucketShareMy(bucketShareMyList: MutableList<BucketShareMy>, memberEmail: String, bucket: Bucket): Boolean {
        for (bucketShareMy in bucketShareMyList)
            if(bucketShareMy.member.email == memberEmail && bucketShareMy.bucket.id == bucket.id)
                return true

        return false
    }
}