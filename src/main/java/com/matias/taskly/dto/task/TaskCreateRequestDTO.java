package com.matias.taskly.dto.task;

import com.matias.taskly.model.Priority;

import java.time.LocalDateTime;

public record TaskCreateRequestDTO(
        String title,
        String description,
        Priority priority,
        LocalDateTime dateLimit
) {}