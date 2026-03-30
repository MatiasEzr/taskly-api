package com.matias.taskly.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequestDTO(@NotBlank String email, @NotBlank String password)
{}
