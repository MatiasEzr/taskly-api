# Taskly API

REST API para gestión de tareas personales. Permite registrar usuarios, iniciar sesión y administrar tareas pendientes con historial de completadas.

---

## Tecnologías

- Java 21
- Spring Boot 3.5.11
- Spring Data JPA + Hibernate
- PostgreSQL
- Maven

---

## Requisitos previos

- Java 21
- PostgreSQL instalado y corriendo
- Maven

---

## Configuración

Creá el archivo `src/main/resources/application.properties` con tus datos locales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskly_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Este archivo está en `.gitignore` y no se sube al repositorio.

---

## Cómo correr el proyecto

```bash
# Clonar el repositorio
git clone git clone https://github.com/MatiasEzr/taskly-api.git

# Entrar a la carpeta
cd taskly-api

# Correr el proyecto
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`

---

## Endpoints

### Autenticación

| Método | Endpoint | Descripción | Status Codes |
|--------|----------|-------------|--------------|
| POST | `/auth/register` | Registrar usuario | 201, 400, 409 |
| POST | `/auth/login` | Iniciar sesión | 200, 401, 404 |

### Tareas

| Método | Endpoint | Descripción | Status Codes |
|--------|----------|-------------|--------------|
| POST | `/users/{userId}/tasks` | Crear tarea | 201, 400, 404 |
| GET | `/users/{userId}/tasks` | Listar tareas del usuario | 200, 404 |
| GET | `/tasks/{id}` | Obtener tarea por ID | 200, 404 |
| PUT | `/tasks/{id}` | Actualizar tarea completa | 200, 400, 404 |
| PATCH | `/tasks/{id}` | Marcar como completada | 200, 404 |
| DELETE | `/tasks/{id}` | Eliminar tarea | 204, 404 |

---

## Ejemplos de uso

### Registrar usuario
```http
POST /auth/register
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan@email.com",
  "password": "1234"
}
```

### Iniciar sesión
```http
POST /auth/login
Content-Type: application/json

{
  "email": "juan@email.com",
  "password": "1234"
}
```

### Crear tarea
```http
POST /users/1/tasks
Content-Type: application/json

{
  "title": "Estudiar Spring Boot",
  "description": "Practicar JPA",
  "priority": "HIGH",
  "dateLimit": "2026-03-20T23:59:00"
}
```

### Marcar tarea como completada
```http
PATCH /tasks/1
Content-Type: application/json

{
  "completed": true
}
```

---

## Formato de error

Todos los errores siguen este formato estándar:

```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Tarea con id 99 no encontrada",
  "path": "/tasks/99",
  "timestamp": "2026-03-16T10:05:00Z"
}
```

---

## Autor

Matias Ezr

---

## Licencia

MIT