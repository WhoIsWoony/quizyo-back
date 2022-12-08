package com.whoiswoony.springtutorial

import com.whoiswoony.springtutorial.service.TodoService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.mockk
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class TodoQueryTest:FunSpec({
    val todoRepository = mockk<TodoRepository>()
    val underTest = TodoService(todoRepository)

    test("Test TodoList") {
        //가짜 db의 todo데이터들을 만든다.
        val todos = generateSequence { randomTodoEntity() }
            .distinct()
            .take(randomSmallPlural())
            .toList()

        //repository를 통하 db로부터 불러오는 함수를 호출할때 위 가짜데이터를 리턴하도록한다.
        every { todoRepository.findAll() } returns todos

        //when : Service에서 getTodo라는 함수를 호출하면
        val actual = underTest.getTodo()

        //then : 기존 데이터들을 DTO로 변환한것과 같은 것들이 나오는가?
        actual shouldBe todos.map { it.toDto() }
    }
})

fun randomTodoEntity() = TodoModel(
    id = randomShortAlphanumeric(),
    title = randomShortAlphanumeric(),
    content = randomShortAlphanumeric(),
    updatedDate = LocalDateTime.now(),
    createdDate = LocalDateTime.now(),
)
fun randomSmallPlural(): Int = RandomUtils.nextInt(2, 10)
fun randomShortAlphanumeric(): String = RandomStringUtils.randomAlphanumeric(randomSmallPlural())