package com.workshop.todo.repository;

import com.workshop.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Todo Repository
 *
 * Provides database operations for Todo entities.
 * Extends JpaRepository to inherit common CRUD operations.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Find all todos by completion status
     *
     * @param completed the completion status
     * @return list of todos with the specified completion status
     */
    List<Todo> findByCompleted(Boolean completed);

    /**
     * Find todos by title containing a search term (case-insensitive)
     *
     * @param title the search term
     * @return list of todos matching the search term
     */
    List<Todo> findByTitleContainingIgnoreCase(String title);
}

