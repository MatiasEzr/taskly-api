package com.matias.taskly.dto.task;

import java.time.LocalDateTime;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        String priority,       // se devuelve como String (ej: "LOW", "MEDIUM", "HIGH")
        Boolean completed,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        LocalDateTime dateLimit,
        Long userId            // en vez de mandar todo el objeto User, solo su id

) {}