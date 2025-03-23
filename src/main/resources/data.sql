INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- Insert Users
--INSERT INTO users (email, password, registered_at)
--VALUES ('admin@example.com', '$2a$10$EIXN0J8Qz1l6bW6bW8Z8qO8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8', '2025-03-01 10:00:00');
--INSERT INTO users (email, password, registered_at)
--VALUES ('user1@example.com', '$2a$10$EIXN0J8Qz1l6bW6bW8Z8qO8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8', '2025-03-02 12:00:00');
--INSERT INTO users (email, password, registered_at)
--VALUES ('user2@example.com', '$2a$10$EIXN0J8Qz1l6bW6bW8Z8qO8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8Q8Z8', '2025-03-03 14:00:00');

-- Insert User-Role Relationships (users_roles)
--INSERT INTO users_roles (user_id, role_id)
--VALUES (1, 1); -- admin@example.com -> ADMIN
--INSERT INTO users_roles (user_id, role_id)
--VALUES (2, 2); -- user1@example.com -> USER
--INSERT INTO users_roles (user_id, role_id)
--VALUES (3, 2); -- user2@example.com -> USER
--
---- Insert Tasks
--INSERT INTO tasks (name, description, user_id, status, created_at)
--VALUES ('Admin Task 1', 'Admin task description', 1, 'NOT_STARTED', '2025-03-01 11:00:00');
--INSERT INTO tasks (name, description, user_id, status, created_at)
--VALUES ('User1 Task 1', 'First task for user1', 2, 'STARTED', '2025-03-02 13:00:00');
--INSERT INTO tasks (name, description, user_id, status, created_at)
--VALUES ('User1 Task 2', 'Second task for user1', 2, 'COMPLETED', '2025-03-02 14:00:00');
--INSERT INTO tasks (name, description, user_id, status, created_at)
--VALUES ('User2 Task 1', 'Task for user2', 3, 'NOT_STARTED', '2025-03-03 15:00:00');
--INSERT INTO tasks (name, description, user_id, status, created_at)
--VALUES ('User2 Task 2', NULL, 3, 'STARTED', '2025-03-03 16:00:00');