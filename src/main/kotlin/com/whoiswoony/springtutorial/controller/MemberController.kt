package com.whoiswoony.springtutorial.controller

import com.whoiswoony.springtutorial.dto.member.IssueTemporalPasswordRequest
import com.whoiswoony.springtutorial.service.member.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name="MEMBER API", description = "멤버들의 개인정보를 다루는 API입니다.")
@RestController
@RequestMapping("/member/")
class MemberController(private val memberService: MemberService) {
    @Operation(summary = "닉네임변경", description = "닉네임 변경 테스트")
    @PostMapping("/changeNickname")
    fun changeNickname(): String {
        return "임시테스트"
    }

    @Operation(summary = "임시 비밀번호 발급", description = "(email, nickname) => String")
    @PostMapping("/issueTemporalPassword")
    fun issueTemporalPassword(@RequestBody request: IssueTemporalPasswordRequest): String {
        return memberService.issueTemporalPassword(request)
    }
}