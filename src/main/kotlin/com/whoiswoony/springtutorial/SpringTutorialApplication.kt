package com.whoiswoony.springtutorial

import mu.KotlinLogging
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class SpringTutorialApplication

val logger = KotlinLogging.logger{}
const val TEST_URL = "http://localhost:4000"

fun main(args: Array<String>) {
	logger.info ("H2 DB : $TEST_URL/h2-console")
	logger.info ("SWAGGER : $TEST_URL/swagger-ui/index.html")

	runApplication<SpringTutorialApplication>(*args)
}
