package com.ksantiago.springcloud.products.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ClientDto(
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @Min(value = 18)
        int age) {
}
