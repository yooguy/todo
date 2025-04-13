package com.example.todo.configuration

import com.example.todo.domain.Todo
import com.example.todo.repository.TodoRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.random.Random

@Configuration
class DataInitializer(
    private val todoRepository: TodoRepository
) : ApplicationListener<ContextRefreshedEvent> {

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        insertRandomTodos()
    }

    private fun insertRandomTodos() {
        // 매번 30개 새로 넣고 싶으면 count 체크 없이 바로 삽입
        // todoRepository.deleteAll()

        val todos = (1..10).map {
            Todo(
                title = "Random Todo #$it",
                completed = Random.nextBoolean(),
                startDate = LocalDate.now().minusDays(Random.nextLong(0, 30)),
                endDate = LocalDate.now().plusDays(Random.nextLong(0, 30))
            )
        }
        todoRepository.saveAll(todos)
    }
}