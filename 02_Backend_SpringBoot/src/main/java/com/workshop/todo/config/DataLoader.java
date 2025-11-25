package com.workshop.todo.config;

import com.workshop.todo.model.Todo;
import com.workshop.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Data Loader
 *
 * Loads sample data into the database on application startup.
 * Useful for testing and demonstration purposes.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final TodoRepository todoRepository;

    @Autowired
    public DataLoader(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        todoRepository.deleteAll();

        // Create sample todos
        Todo todo1 = new Todo();
        todo1.setTitle("Complete Module 01 - Getting Started");
        todo1.setDescription("Set up the development environment and verify all tools are installed");
        todo1.setCompleted(true);

        Todo todo2 = new Todo();
        todo2.setTitle("Explore Module 02 - Backend API");
        todo2.setDescription("Understand the Spring Boot application structure and REST API endpoints");
        todo2.setCompleted(false);

        Todo todo3 = new Todo();
        todo3.setTitle("Build Module 03 - React Frontend");
        todo3.setDescription("Create the React application and connect it to the backend API");
        todo3.setCompleted(false);

        Todo todo4 = new Todo();
        todo4.setTitle("Configure CI/CD Pipeline");
        todo4.setDescription("Set up GitHub Actions for automated deployment");
        todo4.setCompleted(false);

        Todo todo5 = new Todo();
        todo5.setTitle("Deploy to Production");
        todo5.setDescription("Deploy backend to Render and frontend to GitHub Pages");
        todo5.setCompleted(false);

        // Save todos
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        todoRepository.save(todo4);
        todoRepository.save(todo5);

        System.out.println("Sample data loaded successfully!");
        System.out.println("Total todos: " + todoRepository.count());
    }
}

