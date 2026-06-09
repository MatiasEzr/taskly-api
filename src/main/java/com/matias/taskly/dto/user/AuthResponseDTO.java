package com.matias.taskly.dto.user;

/**
 * Respuesta para login y register.
 *
 * El token JWT se incluye para que el frontend lo guarde en localStorage
 * y lo mande en el header Authorization de cada request posterior.
 */
public record AuthResponseDTO(
        Long id,
        String nickname,
        String email,
        String token    // JWT generado al autenticarse — null en el register si no querés auto-login
) {}
 