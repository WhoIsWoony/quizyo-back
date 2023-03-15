package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.config.security.JwtUtils
import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.bucket.*
import com.whoiswoony.springtutorial.domain.member.*
import com.whoiswoony.springtutorial.domain.quiz.Quiz
import com.whoiswoony.springtutorial.dto.bucket.AddBucketShareMyRequest
import com.whoiswoony.springtutorial.service.bucket.BucketService
import com.whoiswoony.springtutorial.service.bucket.BucketShareMyService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.sql.Date
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.servlet.http.HttpServletRequest


@ExtendWith(MockKExtension::class)
class BucketTest :StringSpec({
    val memberRepository = mockk<MemberRepository>()
    val bucketRepository = mockk<BucketRepository>(relaxed = true)
    val bucketViewRepository = mockk<BucketViewRepository>()
    val bucketRepositorySupport = mockk<BucketRepositorySupport>()
    val bucketShareMyRepository = mockk<BucketShareMyRepository>()
    val resetCode = ResetCode("")
    val jwtUtils = mockk<JwtUtils>()
    val request: HttpServletRequest = mockk<HttpServletRequest>()

    val bucketService = BucketService(
        memberRepository = memberRepository,
        bucketRepository = bucketRepository,
        bucketRepositorySupport = bucketRepositorySupport,
        bucketViewRepository = bucketViewRepository,
        jwtUtils = jwtUtils,
    )
    val bucketShareMyService = BucketShareMyService(
        memberRepository = memberRepository,
        bucketShareMyRepository = bucketShareMyRepository,
        bucketRepository = bucketRepository
    )

    "없는 퀴즈셋 조회 불가능"{
        //given
        val bucketId : Long = 1

        every { bucketRepository.findByIdOrNull(bucketId) } returns null

        //when
        val exception = shouldThrow<RuntimeException> { bucketService.addBucketView(bucketId, request) }

        //then
        exception shouldBe CustomException(ErrorCode.NOT_FOUND_BUCKET)
    }

    "24시간 이내 조회한 버킷 조회수 업데이트 불가"{
        //given
        val email = "test@test.com"
        val password = "test123!"
        val nickname = "test"
        val member = Member(email, password, nickname, resetCode)

        val bucketId : Long = 1
        val bucketTitle = "test"
        val bucketDescription = "INVALID_IPADDRESS_FORM"
        val bucketViews = mutableListOf<BucketView>()
        val bucketShares = mutableListOf<BucketShareMy>()
        val bucketQuizs = mutableListOf<Quiz>()
        val bucket = Bucket(bucketTitle, bucketDescription, member, bucketViews, bucketShares, bucketQuizs, bucketId)
        val refreshToken = "refreshToken"

        val updatedBucketView = mutableListOf<BucketView>()
        val date = Date.valueOf(LocalDate.now())
        val time = Time.valueOf(LocalTime.now())
        updatedBucketView.add(BucketView(bucket, email, date , time , bucketId))

        val updatedBucket = Bucket(bucketTitle, bucketDescription, member, updatedBucketView, bucketShares, bucketQuizs, bucketId)

        every { bucketRepository.findByIdOrNull(bucketId) } returns updatedBucket
        every { jwtUtils.resolveToken(request) } returns refreshToken
        every { jwtUtils.getAuthentication(refreshToken).name } returns email

        //when
        val exception = shouldThrow<RuntimeException> { bucketService.addBucketView(bucketId, request) }

        //then
        exception shouldBe CustomException(ErrorCode.INVALID_BUCKET_VIEW_UPDATE_TIME)
    }

    "이미 퍼온 버킷 다시 퍼오기 불가"{
        //given
        val bucketMakerEmail = "maker@maker.com"
        val bucketMakerPassword = "maker123!"
        val bucketMakerNickname = "maker"
        val bucketMaker = Member(bucketMakerEmail, bucketMakerPassword, bucketMakerNickname, resetCode)

        val bucketSharerEmail = "test@test.com"
        val bucketSharerPassword = "test123!"
        val bucketSharerNickname = "test"
        val bucketSharer = Member(bucketSharerEmail, bucketSharerPassword, bucketSharerNickname, resetCode)

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

        every { memberRepository.findByEmail(any()) } returns Member(email, password, nickname, resetCode)
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
        val member = Member(email, password, nickname, resetCode)

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