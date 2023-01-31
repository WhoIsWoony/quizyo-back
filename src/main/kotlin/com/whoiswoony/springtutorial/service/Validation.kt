package com.whoiswoony.springtutorial.service

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

    fun ipAddressValidation(ipAddress:String): Boolean {
        // 정규식 : IP 주소 형식확인
        // ?x         <-  한자리, 두자리 일때 앞에 한자리 없을수 있게 하고 [1-9]?[0-9] 시작 0으로 하는 두자리는 안되게 처리, 한자리 '0'은 허용
        // 1xx        <- 1로 시작하는 세자리는 [0-9] [0-9]
        // 20x, 21x, 23x, 24x,  <- 2로 시작하는 세자리는 두번째 자리 제한 [0-4] , 셋째자리 [0-9]
        // 250, 251, 252, 253, 254, 255  <- 25로 시작 하는 세자리는 셋째자리 제한 [0-5]
        return regexValidation("""^([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([1-9]?[0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$""",ipAddress)
    }
}