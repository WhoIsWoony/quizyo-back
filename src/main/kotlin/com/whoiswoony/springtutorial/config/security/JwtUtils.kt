package com.whoiswoony.springtutorial.config.security

import com.whoiswoony.springtutorial.controller.exception.CustomException
import com.whoiswoony.springtutorial.controller.exception.ErrorCode
import com.whoiswoony.springtutorial.domain.member.Authority
import com.whoiswoony.springtutorial.logger
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

@Component
class JwtUtils(private val userDetailsService: UserDetailsService) {

    @Value("\${jwt.access-token-secret}")
    lateinit var accessTokenSecret: String

    @Value("\${jwt.refresh-token-secret}")
    lateinit var refreshTokenSecret: String

    //microSec * Sec * Min
    // Token 만료시간 : 1Hour
    private val accessExpireTime = 1000L * 60 * 60

    // TestToken 만료시간 : 5 sec
    private val accessExpireTimeTest = 1000L * 5

    // RefreshToken 만료시간 : 1 Day
    private val refreshExpireTime = 1000L * 60 * 60 * 24

    //Token 생성
    fun createAccessToken(email:String, roles:MutableSet<Authority>) : String {
        //jwt 권한 정보 : 고유ID, 권한
        val claims = Jwts.claims().setSubject(email)
        claims["roles"] = roles

        //jwt 시간 정보 : 현재시간, 만료시간
        val now = Date()
        val expiredAt = Date(now.time + accessExpireTime)

        //jwt 생성 : 위 정보 바탕으로
        val jwt = Jwts.builder()
        jwt.setClaims(claims)
        jwt.setIssuedAt(now)
        jwt.setExpiration(expiredAt)
        jwt.signWith(SignatureAlgorithm.HS256, accessTokenSecret)

        return jwt.compact()
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
                .map { charset.random() }
                .joinToString("")
    }

    fun createRefreshToken(): String {
        //jwt 권한 정보 : 고유ID, 권한
        val claims = Jwts.claims().setSubject(getRandomString(10))

        //jwt 시간 정보 : 현재시간, 만료시간
        val now = Date()
        val expiredAt = Date(now.time + refreshExpireTime)

        //jwt 생성 : 위 정보 바탕으로
        val jwt = Jwts.builder()
        jwt.setClaims(claims)
        jwt.setIssuedAt(now)
        jwt.setExpiration(expiredAt)
        jwt.signWith(SignatureAlgorithm.HS256, refreshTokenSecret)

        return jwt.compact()
    }

    fun createRefreshTokenCookie(refreshToken:String): Cookie {
        // create a cookie
        val cookie = Cookie("refreshToken", refreshToken)

        // expires in 1 day
        cookie.maxAge = 1 * 24 * 60 * 60

        // optional properties
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = "/"

        return cookie
    }


    fun deleteRefreshTokenCookie(): Cookie {
        // create a cookie
        val cookie = Cookie("refreshToken", null)

        cookie.maxAge = 0

        // optional properties
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = "/"

        return cookie
    }




    //Token으로부터 Spring Security의 권한 정보 획득
    fun getAuthentication(token:String):Authentication{
        val email = getEmail(token)
        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails,"", userDetails.authorities)
    }

    //Token으로부터 유저 email 획득
    private fun getEmail(token:String): String {
        val parser = Jwts.parser().setSigningKey(accessTokenSecret)
        val claims = parser.parseClaimsJws(token)
        return claims.body.subject
    }

    //Request로 부터 Bearer+Token 추출
    fun resolveToken(request: HttpServletRequest): String? {
        //bearerToken 포함여부 체크
        val bearerToken = request.getHeader("Authorization")

        bearerToken ?: return null
        if(bearerToken.length < "BEARER ".length) return null

        //Beaer 포함여부 체크
        val checkBearer = bearerToken.substring(0, "BEARER ".length)
        if(!checkBearer.equals("BEARER ",ignoreCase = true))
            return null

        //순수 Token 추출
        val token = bearerToken.substring("BEARER ".length)
        return token.trim()
    }

    //Token 검증
    fun isValid(token: String):Boolean{
        try{
            //정상적으로 token을 해석할 수 있으면 valid
            val parser = Jwts.parser().setSigningKey(accessTokenSecret)
            val claims = parser.parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        }catch (e:Exception){
            when(e){
                is SecurityException, is MalformedJwtException, is SignatureException ->{
                    logger.error("잘못된 JWT 서명입니다.")
                    throw CustomException(ErrorCode.INVALID_TOKEN_SIGNATURE)
                }
                is ExpiredJwtException ->{
                    logger.error("만료된 JWT 토큰입니다.")
                    throw CustomException(ErrorCode.EXPIRED_TOKEN)
                }
                is UnsupportedJwtException -> {
                    logger.error("지원되지 않는 JWT 토큰입니다.")
                    throw CustomException(ErrorCode.NOT_ALLOWED_TOKEN)
                }
                is IllegalArgumentException ->{
                    logger.error("JWT 토큰이 잘못되었습니다.")
                    throw CustomException(ErrorCode.INVALID_TOKEN)
                }
            }
        }
        return false
    }
}