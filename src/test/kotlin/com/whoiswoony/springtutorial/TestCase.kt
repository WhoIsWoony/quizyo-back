package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.domain.group.Group
import com.whoiswoony.springtutorial.domain.group.GroupRepository
import com.whoiswoony.springtutorial.dto.group.GroupRequest
import com.whoiswoony.springtutorial.dto.group.GroupResponse
import com.whoiswoony.springtutorial.service.group.GroupService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TestCase: StringSpec({
    val groupRepository = mockk<GroupRepository>()
    val groupService = GroupService(groupRepository)

    "그룹 생성 테스트" {
        //given : repository save = 저장한 entity 반환
        val groupRequest = GroupRequest(title = "일반그룹입니다!", description = "그룹 생성 테스트입니다.")
        every { groupRepository.save(any()) } returns groupRequest.toEntity()

        //when : service를 통해 저장한 결과 반환
        val result = groupService.saveGroup(groupRequest)

        //then
        result shouldBe GroupResponse(title = "일반그룹입니다!", description = "그룹 생성 테스트입니다.")
    }

    "그룹 가져오기 테스트"{
        //given
        val group = Group(id=1, title = "일반그룹입니다!", description = "그룹 생성 테스트입니다.")
        every{ groupRepository.findFirstByOrderByIdAsc() } returns group

        //when
        val result = groupService.getLastAddGroup()

        //then
        result shouldBe group.toResponse()
    }
})