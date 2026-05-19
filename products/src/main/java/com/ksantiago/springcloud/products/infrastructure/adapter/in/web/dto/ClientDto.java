package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ClientDto(
        Long id,
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Last name cannot be blank") String lastName,
        @NotNull(message = "Age is required") @Positive(message = "Age must be positive") Integer age
) {}
