package com.whoiswoony.springtutorial.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtUtils: JwtUtils):WebSecurityCustomizer {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }


    override fun customize(web: WebSecurity) {
        //Security 미적용
        web.ignoring().antMatchers(
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",

            /* 회원가입과 로그인 */

            /* h2 콘솔 */
            "/h2-console/**"

        )
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .httpBasic().disable()  // Http basic Auth  기반으로 로그인 인증창이 뜸.  disable 시에 인증창 뜨지 않음.
            .csrf().disable() // csrf 설정
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and() // 조건별로 요청 허용/제한 설정
            .authorizeRequests()
            .requestMatchers(
                CorsUtils::isPreFlightRequest
            ).permitAll()
            .antMatchers(
                "/auth/**",
                "/bucket/getBucket/**",
                "/bucket/getOneBucket/**",
                "/bucket/addBucketView/**",
                "/bucket/ddd/**",
                "/quiz/getQuiz/**"
            ).permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN") //admin으로 시작하는 요청은 ADMIN 권한이 있는 유저에게만 허용
            .antMatchers(
                "/user/**",
                "/bucket/**",
                "/quiz/**"
            ).hasRole("USER") //user 로 시작하는 요청은 USER 권한이 있는 유저에게만 허용
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


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000","https://quizyo.develop.woonyhouse.com")
        configuration.allowedMethods = listOf("HEAD", "GET", "POST", "PUT")
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}