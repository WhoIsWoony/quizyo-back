package com.whoiswoony.springtutorial.domain.bucket

import com.querydsl.jpa.impl.JPAQueryFactory
import com.whoiswoony.springtutorial.domain.bucket.QBucketView.bucketView
import com.whoiswoony.springtutorial.dto.bucket.BucketResponse
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BucketRepositorySupport(private val jpaQueryFactory:JPAQueryFactory):QuerydslRepositorySupport(Bucket::class.java){
    fun findTop10(): MutableList<BucketResponse> {
        //퀴즈셋 조회수 카운트 서브쿼리
        val result = jpaQueryFactory
            .selectFrom(bucketView)
            .groupBy(bucketView.bucket)
            .orderBy(bucketView.bucket.count().desc())
            .limit(10)
            .fetch()

        return result.map{
            BucketResponse(
                it.bucket.title,
                it.bucket.description,
                it.bucket.bucketShares.size ,
                it.bucket.views.size,
                it.bucket.id
            )
        }.toMutableList()
    }
}