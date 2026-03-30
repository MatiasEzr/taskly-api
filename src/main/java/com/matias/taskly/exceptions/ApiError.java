package com.matias.taskly.exceptions;

import java.time.Instant;

public record ApiError(
        String code,        // identificador del error   → "USER_NOT_FOUND"
        String message,     // descripción legible        → "User with email juan@email.com not found"
        String path,        // endpoint que falló         → "/auth/login"
        Instant timestamp   // cuándo ocurrió el error    → "2026-03-16T10:05:00Z")
)
{

}