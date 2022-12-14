package com.whoiswoony.springtutorial.service.group

import com.whoiswoony.springtutorial.domain.group.Group
import com.whoiswoony.springtutorial.domain.group.GroupRepository
import com.whoiswoony.springtutorial.dto.group.GroupRequest
import com.whoiswoony.springtutorial.dto.group.GroupResponse
import org.springframework.stereotype.Service

@Service
class GroupService(private val groupRepository: GroupRepository) {
    fun saveGroup(groupRequest: GroupRequest): GroupResponse {
        var group = groupRequest.toEntity()
        group = groupRepository.save(group)
        return group.toResponse()
    }

    fun getLastAddGroup():GroupResponse {
        val group = groupRepository.findFirstByOrderByIdAsc()
        return group.toResponse()
    }
}