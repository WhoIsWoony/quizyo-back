package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.*
import com.whoiswoony.springtutorial.domain.member.Member
import com.whoiswoony.springtutorial.domain.member.MemberRepository
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import com.whoiswoony.springtutorial.dto.bucket.AddBucketShareMyRequest
import com.whoiswoony.springtutorial.service.Validation
import com.whoiswoony.springtutorial.service.bucket.BucketService
import com.whoiswoony.springtutorial.service.bucket.BucketShareMyService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BucketTest :StringSpec({
    val memberRepository = mockk<MemberRepository>()
    val bucketRepository = mockk<BucketRepository>(relaxed = true)
    val bucketViewRepository = mockk<BucketViewRepository>()
    val bucketRepositorySupport = mockk<BucketRepositorySupport>()
    val bucketShareMyRepository = mockk<BucketShareMyRepository>()
    val validation = mockk<Validation>()

    val bucketService = BucketService(
        memberRepository = memberRepository,
        bucketRepository = bucketRepository,
        bucketRepositorySupport = bucketRepositorySupport,
        bucketViewRepository = bucketViewRepository,
        validation = validation
    )
    val bucketShareMyService = BucketShareMyService(
        memberRepository = memberRepository,
        bucketShareMyRepository = bucketShareMyRepository,
        bucketRepository = bucketRepository
    )

    "IP 주소 형식 오류"{

    }

    "이미 퍼온 버킷 다시 퍼오기 불가"{
        //given
        val bucketMakerEmail = "maker@maker.com"
        val bucketMakerPassword = "maker123!"
        val bucketMakerNickname = "maker"
        val bucketMaker = Member(bucketMakerEmail, bucketMakerPassword, bucketMakerNickname)

        val bucketSharerEmail = "test@test.com"
        val bucketSharerPassword = "test123!"
        val bucketSharerNickname = "test"
        val bucketSharer = Member(bucketSharerEmail, bucketSharerPassword, bucketSharerNickname)

        val bucketId : Long = 1
        val bucketTitle = "test"
        val bucketDescription = "INVALID_SHARE_BUCKET TEST"
        val bucketViews = mutableListOf<BucketView>()
        val bucketShares = mutableListOf<BucketShareMy>()
        val bucketQuizs = mutableListOf<Quiz>()
        val bucket = Bucket(bucketTitle, bucketDescription, bucketMaker, bucketViews, bucketShares, bucketQuizs, bucketId)

        val addBucketShareMyRequest = AddBucketShareMyRequest(bucketId)
        bucketSharer.bucketShares.add(BucketShareMy(bucket, bucketSharer))

        every { memberRepository.findByEmail(any()) } returns bucketSharer
        every { bucketRepository.findById(any()).orElse(null) } returns bucket

        //when
        val exception = shouldThrow<RuntimeException> { bucketShareMyService.addBucketShareMy(bucketSharerEmail, addBucketShareMyRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.DUPLICATE_BUCKET_SHARE_MY)
    }
    
    "존재하지 않는 Bucket 퍼오기 불가"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val nickname = "test"
        val fakeBucketId : Long = 1
        val fakeAddBucketShareMyRequest = AddBucketShareMyRequest(fakeBucketId)

        every { memberRepository.findByEmail(any()) } returns Member(email, password, nickname)
        every { bucketRepository.findById(fakeBucketId).orElse(null) } returns null

        //when
        val exception = shouldThrow<RuntimeException> { bucketShareMyService.addBucketShareMy(email, fakeAddBucketShareMyRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.NOT_FOUND_BUCKET)
    }

    "자신이 만든 Bucket 퍼오기 불가"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val nickname = "test"
        val member = Member(email, password, nickname)

        val bucketId : Long = 1
        val bucketTitle = "test"
        val bucketDescription = "INVALID_SHARE_BUCKET TEST"
        val bucketViews = mutableListOf<BucketView>()
        val bucketShares = mutableListOf<BucketShareMy>()
        val bucketQuizs = mutableListOf<Quiz>()
        val addBucketShareMyRequest = AddBucketShareMyRequest(bucketId)
        val bucket = Bucket(bucketTitle, bucketDescription, member, bucketViews, bucketShares, bucketQuizs, bucketId)

        every { memberRepository.findByEmail(any()) } returns member
        every { bucketRepository.findById(any()).orElse(null) } returns bucket

        //when
        val exception = shouldThrow<RuntimeException> { bucketShareMyService.addBucketShareMy(email, addBucketShareMyRequest) }

        //then
        exception shouldBe CustomException(ErrorCode.INVALID_BUCKET_SHARE)
    }
})