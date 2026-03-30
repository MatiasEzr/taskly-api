-- =========================
-- INSERT USERS
-- =========================

INSERT INTO users (name, email, password, date_of_birth, created_at)
VALUES
    ('Matias Lopez', 'matias@example.com', '123456', '2000-05-10', NOW()),
    ('Ana Torres', 'ana@example.com', 'password123', '1998-11-22', NOW()),
    ('Carlos Diaz', 'carlos@example.com', 'qwerty', '1995-03-15', NOW());


-- =========================
-- INSERT TASKS
-- =========================

-- user_id = 1 (Matias)
INSERT INTO tasks (title, description, priority, completed, created_at, completed_at, date_limit, user_id)
VALUES
    ('Estudiar Spring Boot', 'Repasar controllers y services', 'HIGH', false, NOW(), NULL, NOW() + INTERVAL '3 days', 1),

    ('Hacer TP de la facultad', 'Completar backend del proyecto', 'MEDIUM', true, NOW(), NOW(), NOW() + INTERVAL '1 day', 1);


-- user_id = 2 (Ana)
INSERT INTO tasks (title, description, priority, completed, created_at, completed_at, date_limit, user_id)
VALUES
    ('Ir al gimnasio', 'Rutina de piernas', 'LOW', false, NOW(), NULL, NOW() + INTERVAL '2 days', 2);


-- user_id = 3 (Carlos)
INSERT INTO tasks (title, description, priority, completed, created_at, completed_at, date_limit, user_id)
VALUES
    ('Leer libro', 'Clean Code capítulos 1-3', 'MEDIUM', true, NOW(), NOW(), NOW() + INTERVAL '5 days', 3);