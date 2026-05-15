package com.ksantiago.springcloud.products.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record ProductDto(
        @NotBlank
        String name,
        @NotNull
        @Min(value = 10, message = "El valor debe ser mayo a 10")
        Double price
) { }
