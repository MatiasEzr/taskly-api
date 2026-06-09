-- =========================
-- INSERT USERS
-- =========================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users (
    nickname,
    email,
    password,
    date_of_birth,
    role
)
VALUES (
           'MatiasPro',
           'matias@example.com',
           crypt('123456', gen_salt('bf')),
           '2000-05-10',
           'USER'
       ),
    (
        'AnaTorres',
        'ana@example.com',
        crypt('password123', gen_salt('bf')),
        '1998-11-22',
        'USER'
    ),
    (
    'CarlosDev',
    'carlos@example.com',
    crypt('123456', gen_salt('bf')),
    '1999-08-15',
    'USER'
    );



-- =========================
-- INSERT TASKS
-- =========================

-- user_id = 1 (Matias)

INSERT INTO tasks (
    title,
    description,
    priority,
    completed,
    created_at,
    completed_at,
    date_limit,
    user_id
)
VALUES
    (
        'Estudiar Spring Boot',
        'Repasar controllers y services',
        'HIGH',
        false,
        NOW(),
        NULL,
        NOW() + INTERVAL '3 days',
        1
    ),
    (
        'Hacer TP de la facultad',
        'Completar backend del proyecto',
        'MEDIUM',
        true,
        NOW(),
        NOW(),
        NOW() + INTERVAL '1 day',
        1
    );



-- user_id = 2 (Ana)

INSERT INTO tasks (
    title,
    description,
    priority,
    completed,
    created_at,
    completed_at,
    date_limit,
    user_id
)
VALUES
    (
        'Ir al gimnasio',
        'Rutina de piernas',
        'LOW',
        false,
        NOW(),
        NULL,
        NOW() + INTERVAL '2 days',
        2
    );



-- user_id = 3 (Carlos)

INSERT INTO tasks (
    title,
    description,
    priority,
    completed,
    created_at,
    completed_at,
    date_limit,
    user_id
)
VALUES
    (
        'Leer libro',
        'Clean Code capítulos 1-3',
        'MEDIUM',
        true,
        NOW(),
        NOW(),
        NOW() + INTERVAL '5 days',
        3
    );
