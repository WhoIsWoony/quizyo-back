package com.whoiswoony.springtutorial.service.member.Util

import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class Validation {
    private fun regexValidation(regularExpression:String, target:String): Boolean {
        // pattern compile
        val compiledPattern = Pattern.compile(regularExpression)
        // 패턴 비교를 위한 Matcher 인스턴스 생성
        val patternMatcher = compiledPattern.matcher(target)
        // 비교 결과(Boolean type) 반환
        return patternMatcher.find()
    }

    fun emailValidation(email:String): Boolean {
        // 정규식 : email 형식 확인
        return regexValidation("[a-z0-9]+@[a-z]+.[a-z]{2,3}", email)
    }

    fun passwordValidation(password:String): Boolean {
        // 정규식 : 비밀번호는 최소 한개의 문자, 숫자, 특수 문자로 이루어진 8자 이상의 조합
        return regexValidation("""^(?=.*[A-Za-z])(?=.*\d)(?=.*[${'$'}@${'$'}!%*#?&])[A-Za-z\d${'$'}@${'$'}!%*#?&]{8,}${'$'}""", password)
    }
}