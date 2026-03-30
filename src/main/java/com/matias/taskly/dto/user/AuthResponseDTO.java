package com.matias.taskly.dto.user;

//Respuesta para login y register, se modificara si el front lo necesita
public record AuthResponseDTO(
        Long id,
        String name,
        String email
) {}