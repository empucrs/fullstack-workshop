# Module 02 - Backend Application (Spring Boot)

## Overview

Explore and test the Spring Boot REST API for managing TODO items.

## Tech Stack

- Spring Boot 3.2, Spring Web, Spring Data JPA
- H2 Database (in-memory)
- Maven

## Project Structure

```
02_Backend_SpringBoot/
├── src/main/java/com/workshop/todo/
│   ├── TodoApplication.java       # Main app
│   ├── controller/                # REST endpoints
│   ├── service/                   # Business logic
│   ├── model/                     # Entity
│   ├── repository/                # Data access
│   └── config/                    # Configuration
└── src/main/resources/
    └── application.properties     # Config
```

## TODO Model

```json
{
  "id": 1,
  "title": "Complete workshop",
  "description": "Finish all modules",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

## API Endpoints

Base URL: `http://localhost:8080/api`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/todos` | Get all todos |
| GET | `/todos?completed=true` | Filter by status |
| GET | `/todos/{id}` | Get one todo |
| GET | `/todos/search?title=keyword` | Search todos |
| POST | `/todos` | Create todo |
| PUT | `/todos/{id}` | Update todo |
| PATCH | `/todos/{id}/toggle` | Toggle status |
| DELETE | `/todos/{id}` | Delete todo |
| GET | `/todos/health` | Health check |

## Quick Start

### 1. Build

```bash
cd 02_Backend_SpringBoot
./mvnw clean install
```

### 2. Run

```bash
./mvnw spring-boot:run
```

Server starts on: http://localhost:8080

### 3. Test API

**Using curl:**
```bash
# Get all todos
curl http://localhost:8080/api/todos

# Create todo
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Testing","completed":false}'

# Health check
curl http://localhost:8080/api/todos/health
```

**Using browser:**
- http://localhost:8080/api/todos
- http://localhost:8080/api/h2-console (database console)

## Key Files

**Main Application:**
```java
// TodoApplication.java - Entry point
@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
```

**REST Controller:**
```java
// TodoController.java - REST endpoints
@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    // GET, POST, PUT, PATCH, DELETE methods
}
```

**Configuration:**
```properties
# application.properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:tododb
spring.h2.console.enabled=true
server.servlet.context-path=/api
```

## Troubleshooting

**Port 8080 in use:** Kill process with `lsof -i :8080` then `kill -9 PID`
**Build fails:** Run `./mvnw clean install -U`
**CORS errors:** Check `WebConfig.java` has correct origins

---

**Next:** [Module 03 - Frontend Application](../03_Frontend_React/)
