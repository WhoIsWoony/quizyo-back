package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.controller.TodoApi
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TodoApiTest:FunSpec({
	val todoApi = mockk<TodoApi>()

	test("Test helloTodo") {
		every { todoApi.helloTodo() } returns "Hello Todo"
		todoApi.helloTodo() shouldBe "Hello Todo"
	}
})