package com.whoiswoony.springtutorial.domain.group

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository:JpaRepository<Group, Long>{
    fun findFirstByOrderByIdAsc():Group
}