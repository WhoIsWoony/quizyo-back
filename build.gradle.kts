import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.6"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("org.springdoc.openapi-gradle-plugin") version "1.5.0"	//Swagger

	kotlin("jvm") version "1.7.10"
	kotlin("kapt") version "1.7.10"
	kotlin("plugin.spring") version "1.7.10"

	kotlin("plugin.jpa") version "1.7.10" //JPA
	kotlin("plugin.allopen") version "1.7.10" // allOpen에서 지정한 어노테이션으로 만든 클래스에 open 키워드 적용
	// - Hibernate가 사용하는 Reflection API에서 Entity를 만들기 위해 인자 없는 기본 생성자가 필요함
	kotlin("plugin.noarg") version "1.7.10" // 인자 없는 기본 생성자를 자동 생성

}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

noArg {
	annotation("jakarta.persistence.Entity")
}

group = "com.whoiswoony"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	//Spring-Boot
	implementation("org.springframework.boot:spring-boot-starter") // Spring boot 를 이용하기 위한 위존성
	implementation("org.springframework.boot:spring-boot-starter-web") // Spring boot web과 관련 controller 작성을 위함
	implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin 런타임 라이브러리 용량을 줄이기 위한 위존성
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // Kotlin 필수 기능, let, apply, use, synchronized 등

	//JPA = MariaDB
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client") //Kotlin과 Mariadb을 연결
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA - Hibernate 의존성 설정
	implementation("org.jetbrains.kotlin:kotlin-allopen") //Kotlin에서 Hibernate를 원할하게 사용하기 위함
	implementation("org.jetbrains.kotlin:kotlin-noarg") //Kotlin에서 Hibernate를 원할하게 사용하기 위함
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //Json 라이브러리

	//QueryDSL
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

	//Swagger = springdoc-openapi
	implementation("org.springdoc:springdoc-openapi-ui:1.6.13")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.13")

	//Logger
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

	//JWT + Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.9.1") // jwt 방식을 사용

	//H2 Database
	runtimeOnly("com.h2database:h2")

	//JUnit = Kotest + mockk
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.2")
	testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
