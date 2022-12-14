package com.whoiswoony.springtutorial

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTutorialApplication

val logger = KotlinLogging.logger{}
const val TEST_URL = "http://localhost:8080"
fun main(args: Array<String>) {
	logger.info ("$TEST_URL/h2-console")

	runApplication<SpringTutorialApplication>(*args)
}
