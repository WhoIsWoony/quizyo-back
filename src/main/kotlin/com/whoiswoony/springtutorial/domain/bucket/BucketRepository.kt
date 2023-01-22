package com.whoiswoony.springtutorial.domain.bucket

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BucketRepository : JpaRepository<Bucket, Long>