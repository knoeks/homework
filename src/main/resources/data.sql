INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- Insert Users
--password: Admin1234!
INSERT INTO users (email, password, registered_at)
VALUES ('admin@example.com', '$2a$10$yuCTidHD.13xKMgRZXSZFe3Oz3cDXr8OkqsPdaV2XcFeheH.ZQTJq', '2025-03-01 10:00:00');

--password: User1234!
INSERT INTO users (email, password, registered_at)
VALUES ('user1@example.com', '$2a$10$1r1HDKF84T0bT1py7DATWerQvBHMd1TU0emPoaYfMzxiBP5HdCs0i', '2025-03-02 12:00:00');

--password: User1234!
INSERT INTO users (email, password, registered_at)
VALUES ('user2@example.com', '$2a$10$1r1HDKF84T0bT1py7DATWerQvBHMd1TU0emPoaYfMzxiBP5HdCs0i', '2025-03-03 14:00:00');

-- Insert User-Role Relationships (users_roles)
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1); -- admin@example.com -> ADMIN
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 2); -- user1@example.com -> USER
INSERT INTO users_roles (user_id, role_id)
VALUES (3, 2); -- user2@example.com -> USER

-- Insert Tasks
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('Admin Task 1', 'Admin task description', 1, 'NOT_STARTED', '2025-03-01 11:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 1', 'First task for user1', 2, 'STARTED', '2025-03-02 13:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 2', 'Second task for user1', 2, 'COMPLETED', '2025-03-02 14:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 1', 'Task for user2', 3, 'NOT_STARTED', '2025-03-03 15:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 2', NULL, 3, 'STARTED', '2025-03-03 16:00:00');

-- Admin Tasks
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('Admin Task 2', 'Review system logs', 1, 'STARTED', '2025-03-01 12:30:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('Admin Task 3', 'Update security policies', 1, 'COMPLETED', '2025-03-01 15:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('Admin Task 4', NULL, 1, 'NOT_STARTED', '2025-03-02 09:00:00');

-- User1 Tasks
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 3', 'Prepare project report', 2, 'NOT_STARTED', '2025-03-02 15:30:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 4', 'Test new feature', 2, 'STARTED', '2025-03-03 10:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 5', 'Fix bugs in module A', 2, 'COMPLETED', '2025-03-03 12:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User1 Task 6', NULL, 2, 'NOT_STARTED', '2025-03-03 14:30:00');

-- User2 Tasks
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 3', 'Draft presentation slides', 3, 'COMPLETED', '2025-03-03 17:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 4', 'Attend team meeting', 3, 'STARTED', '2025-03-04 09:30:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 5', 'Submit weekly report', 3, 'NOT_STARTED', '2025-03-04 11:00:00');
INSERT INTO tasks (name, description, user_id, status, created_at)
VALUES ('User2 Task 6', NULL, 3, 'COMPLETED', '2025-03-04 13:00:00');