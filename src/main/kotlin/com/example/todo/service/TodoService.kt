package com.example.todo.service

import com.example.todo.domain.Todo
import com.example.todo.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {

    fun getAllTodos(): List<Todo> = todoRepository.findAll()

    fun createTodo(title: String, startDate: LocalDate?, endDate: LocalDate?): Todo {
        return todoRepository.save(
            Todo(
                title = title,
                startDate = startDate,
                endDate = endDate
            )
        )
    }

    fun toggleTodo(id: Long): Todo {
        val todo = todoRepository.findById(id).orElseThrow { NoSuchElementException("Todo not found") }
        todo.completed = !todo.completed
        return todoRepository.save(todo)
    }

    fun deleteTodo(id: Long) = todoRepository.deleteById(id)
}
