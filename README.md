# Task Management API

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-green) ![Maven](https://img.shields.io/badge/Maven-4.0.0-blue) ![License](https://img.shields.io/badge/License-MIT-yellow)

A RESTful API built with Spring Boot for managing tasks and user authentication. This project demonstrates a secure, scalable backend with JWT-based authentication, data validation, and comprehensive Swagger documentation.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

The **Task Management API** allows users to register, log in, and manage tasks with statuses (`NOT_STARTED`, `STARTED`, `COMPLETED`). It includes role-based access control (ADMIN and USER roles) and integrates Swagger for interactive API documentation. The project showcases modern Java development practices, including Spring Security, JPA/Hibernate, and Bean Validation.

---

## Features

- **User Management**:
  - Register users with email and strong password validation.
  - Login with JWT token generation.
- **Task Management**:
  - Create, update, and retrieve tasks.
  - Cycle task statuses (e.g., NOT_STARTED → STARTED → COMPLETED).
  - Filter tasks by status.
- **Security**:
  - JWT-based authentication for protected endpoints.
  - Role-based authorization (ADMIN can manage all tasks, USER only their own).
- **Documentation**:
  - Swagger UI with detailed endpoint descriptions, examples, and security details.

---

## Technologies

- **Java 17**: Core programming language.
- **Spring Boot 3.2.2**: Framework for building the REST API.
- **Spring Security**: Authentication and authorization.
- **Spring Data JPA**: Database interaction with Hibernate.
- **H2/PostgreSQL**: Database (configurable).
- **Maven**: Dependency management.
- **Springdoc OpenAPI**: Swagger integration for API documentation.
- **Lombok**: Reduces boilerplate code.

---

## Setup

### Prerequisites

- Java 17+
- Maven 3.9.6+
- PostgreSQL (optional, H2 is used by default)
- Git

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/task-management-api.git
   cd task-management-api
2. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run

3. **Access the API**:
- Application: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## API Endpoints

The API is documented with Swagger at `/swagger-ui.html`. Below is a summary:

### User Management (`/api/users`)

| Method | Endpoint       | Description            | Request Body                                    | Response                          |
|--------|----------------|------------------------|------------------------------------------------|-----------------------------------|
| POST   | `/register`    | Register a new user   | `{"email": "user@example.com", "password": "Password123!"}` | 201, User details         |
| POST   | `/login`       | Login and get JWT     | `{"email": "user@example.com", "password": "Password123!"}` | 200, `{"success": true}`, JWT in `Authorization` header |

### Task Management (`/api/tasks`)

| Method | Endpoint             | Description            | Request Body / Params                     | Response                  |
|--------|----------------------|------------------------|-------------------------------------------|---------------------------|
| POST   | `/`                  | Create a new task     | `{"name": "New Task", "description": "Details"}` | 201, Task details         |
| PUT    | `/{id}`              | Update a task         | `{"name": "Updated Task", "status": "STARTED"}` | 200, Updated task         |
| PUT    | `/{id}/cycle-status` | Cycle task status     | None (Path: `id`)                         | 200, Updated task         |
| GET    | `/{id}`              | Get a task by ID      | None (Path: `id`)                         | 200, Task details         |
| GET    | `/`                  | Get tasks by status   | Query: `status=STARTED`                   | 200, List of tasks        |

- **Authentication**: Required for all `/api/tasks` endpoints (Bearer token).
- **Validation**: Enforces constraints (e.g., task name 1-100 chars, password complexity).

---

## Database Schema

The application uses the following tables:

- **`users`**:
  - `id` (PK)
  - `email`
  - `password`
  - `registered_at`
- **`roles`**:
  - `id` (PK)
  - `name` (e.g., ADMIN, USER)
- **`users_roles`**:
  - `user_id` (FK to `users`)
  - `role_id` (FK to `roles`)
- **`tasks`**:
  - `id` (PK)
  - `name`
  - `description`
  - `user_id` (FK to `users`)
  - `status` (e.g., NOT_STARTED, STARTED, COMPLETED)
  - `created_at`

See `src/main/resources/data.sql` for sample data.
