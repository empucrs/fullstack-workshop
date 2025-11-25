package com.workshop.todo.service;

import com.workshop.todo.model.Todo;
import com.workshop.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Todo Service
 *
 * Business logic layer for TODO operations.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Get all todos
     *
     * @return list of all todos
     */
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Get a todo by ID
     *
     * @param id the todo ID
     * @return optional containing the todo if found
     */
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    /**
     * Get todos by completion status
     *
     * @param completed the completion status
     * @return list of todos with the specified status
     */
    public List<Todo> getTodosByStatus(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    /**
     * Search todos by title
     *
     * @param title the search term
     * @return list of matching todos
     */
    public List<Todo> searchTodos(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Create a new todo
     *
     * @param todo the todo to create
     * @return the created todo
     */
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * Update an existing todo
     *
     * @param id the todo ID
     * @param todoDetails the updated todo details
     * @return the updated todo
     * @throws RuntimeException if todo not found
     */
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.getCompleted());

        return todoRepository.save(todo);
    }

    /**
     * Toggle the completion status of a todo
     *
     * @param id the todo ID
     * @return the updated todo
     * @throws RuntimeException if todo not found
     */
    public Todo toggleTodoStatus(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setCompleted(!todo.getCompleted());
        return todoRepository.save(todo);
    }

    /**
     * Delete a todo
     *
     * @param id the todo ID
     * @throws RuntimeException if todo not found
     */
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    /**
     * Delete all todos
     */
    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }
}

