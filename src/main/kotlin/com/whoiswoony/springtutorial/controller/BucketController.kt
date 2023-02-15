package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.config.security.getMemberEmail
import com.whoiswoony.springtutorial.controller.util.getIp
import com.whoiswoony.springtutorial.dto.*
import com.whoiswoony.springtutorial.dto.bucket.*
import com.whoiswoony.springtutorial.logger
import com.whoiswoony.springtutorial.service.bucket.BucketService
import com.whoiswoony.springtutorial.service.bucket.BucketShareMyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Tag(name="BUCKET API", description = "버킷은 퀴즈들을 담고 있는 그룹입니다. 버킷의 생성, 조회, 공유를 관리하는 API입니다.")
@RestController
@RequestMapping("/bucket/")
class BucketController(private val bucketService: BucketService, private val bucketShareMyService: BucketShareMyService) {

    @Operation(summary = "버킷추가", description = "(title, description) =>")
    @PostMapping("/addBucket")
    fun addBucket(@RequestBody addBucketRequest: AddBucketRequest): Long? {
        val memberEmail = getMemberEmail()
        return bucketService.addBucket(memberEmail, addBucketRequest)
    }

    @Operation(summary = "버킷 전체 가져오기", description = "() =>")
    @GetMapping("/getBucket")
    fun getBucket(): MutableList<BucketResponse> {
        return bucketService.getBucket()
    }

    @Operation(summary = "버킷 한개 가져오기", description = "(bucketId) =>")
    @GetMapping("/getOneBucket/{bucketId}")
    fun getOneBucket(@PathVariable bucketId:Long): BucketResponse {
        return bucketService.getOneBucket(bucketId)
    }

    @Operation(summary = "버킷 조회수 증가", description = "(bucketId, ipAddress) =>")
    @GetMapping("/addBucketView/{bucketId}")
    fun addBucketView(@PathVariable bucketId:Long, request: HttpServletRequest): Boolean {
        val ipAddress = getIp(request)
        logger.error { ipAddress }
        bucketService.addBucketView(bucketId, ipAddress)
        return true
    }

    @Operation(summary = "버킷 공유하기", description = "(bucketId)=>")
    @PostMapping("/addBucketShareMy")
    fun addBucketShareMy(@RequestBody addBucketShareMyRequest: AddBucketShareMyRequest){
        val memberEmail = getMemberEmail()
        bucketShareMyService.addBucketShareMy(memberEmail, addBucketShareMyRequest)
    }

    @Operation(summary = "버킷 '내가 생성한' 것을 가져오기", description = "()=>{bucketMy, bucketShareMy}")
    @GetMapping("/getMyBucket")
    fun getMyBucket() : BucketMyResponse? {
        val memberEmail = getMemberEmail()
        val myBuckets = bucketService.getBucketMy(memberEmail)
        val myBucketShares = bucketShareMyService.getBucketShareMy(memberEmail)

        return BucketMyResponse(myBuckets, myBucketShares)
    }

    @Operation(summary = "버킷 조회순 TOP10 가져오기", description = "()=>[BucketResponse]")
    @GetMapping("/getFindTop10")
    fun getFindTop10(): BucketTop10Response {
        return bucketService.getFindTop10()
    }
}