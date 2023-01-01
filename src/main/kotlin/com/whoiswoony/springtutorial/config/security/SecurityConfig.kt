package com.whoiswoony.springtutorial.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtUtils: JwtUtils) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
            .httpBasic().disable() // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
            .csrf().disable() // CORS 설정
            .cors { c: CorsConfigurer<HttpSecurity?> ->
                val source = CorsConfigurationSource { request ->
                    // Cors 허용 패턴
                    val config = CorsConfiguration()
                    config.allowedOrigins = listOf("*")
                    config.allowedMethods = listOf("*")
                    config
                }
                c.configurationSource(source)
            } // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and() // 조건별로 요청 허용/제한 설정
            .authorizeRequests()
            .antMatchers(
                /* swagger v3 */
                "/v3/api-docs/**",
                "/swagger-ui/**",

                /* 회원가입과 로그인은 모두 승인 */
                "/auth/**",
            ).permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")  // /admin으로 시작하는 요청은 ADMIN 권한이 있는 유저에게만 허용
            .antMatchers("/user/**").hasRole("USER")//user 로 시작하는 요청은 USER 권한이 있는 유저에게만 허용
            .anyRequest().denyAll()
            .and() // JWT 인증 필터 적용
            .addFilterBefore(
                JwtAuthenticationFilter(jwtUtils),
                UsernamePasswordAuthenticationFilter::class.java
            ) // 에러 핸들링
            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
        return http.build()
    }
}