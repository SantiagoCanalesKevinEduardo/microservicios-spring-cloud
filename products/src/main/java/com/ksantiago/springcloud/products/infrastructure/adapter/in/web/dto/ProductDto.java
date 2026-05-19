package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductDto(
        Long id,
        @NotBlank(message = "Name cannot be blank") String name,
        @NotNull(message = "Price is required") @Positive(message = "Price must be positive") Double price
) {}
