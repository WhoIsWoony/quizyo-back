package com.whoiswoony.springtutorial.service.group

import com.whoiswoony.springtutorial.domain.group.Group
import com.whoiswoony.springtutorial.domain.group.GroupRepository
import com.whoiswoony.springtutorial.dto.group.GroupRequest
import com.whoiswoony.springtutorial.dto.group.GroupResponse
import org.springframework.stereotype.Service

@Service
class GroupService(private val groupRepository: GroupRepository) {
    fun saveGroup(group: Group): Group {
        return groupRepository.save(group)
    }

    fun getLastAddGroup():Group {
        return groupRepository.findFirstByOrderByIdAsc()
    }
}