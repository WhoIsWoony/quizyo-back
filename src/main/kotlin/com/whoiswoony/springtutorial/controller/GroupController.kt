package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.group.GroupRequest
import com.whoiswoony.springtutorial.dto.group.GroupResponse
import com.whoiswoony.springtutorial.service.group.GroupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="그룹", description = "그룹 관련 api 입니다.")
@RestController
@RequestMapping("/api/v1")
class GroupController(private val groupService: GroupService){
    @Operation(summary = "그룹 생성", description = "그룹을 생성합니다.")
    @PostMapping("/saveGroup")
    fun saveGroup(@RequestBody groupRequest: GroupRequest):GroupResponse {
        var group = groupRequest.toEntity()
        group = groupService.saveGroup(group)
        return group.toResponse()
    }

    @Operation(summary = "가장 최근에 추가된 그룹 반환", description = "가장 최근에 추가된 그룹을 반환합니다.")
    @GetMapping("/getGroup")
    fun getGroup():GroupResponse {
        val group = groupService.getLastAddGroup()
        return group.toResponse()
    }
}