package com.matias.taskly.dto.task;

import com.matias.taskly.model.Priority;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskUpdateRequestDTO(
        @NotBlank String title,
        @Size(max = 255) String description,
        @NotNull Priority priority,
        LocalDateTime dateLimit
) {}