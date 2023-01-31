package com.whoiswoony.springtutorial.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI? {
        val info = Info()
            .version("v1.0.0")
            .title("QuizYo 백엔드 API")
            .description("QuizYo와 함께하는 백엔드 프로젝트햐")

        // SecuritySecheme명
        val jwtSchemeName = "jwtAuth"
        // API 요청헤더에 인증정보 포함
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)
        // SecuritySchemes 등록
        val components = Components()
            .addSecuritySchemes(
                jwtSchemeName, SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP) // HTTP 방식
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ) // 토큰 형식을 지정하는 임의의 문자(Optional)
        return OpenAPI()
            .info(info)
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}