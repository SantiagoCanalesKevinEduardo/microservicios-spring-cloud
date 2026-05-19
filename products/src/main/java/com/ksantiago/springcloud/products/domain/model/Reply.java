package com.ksantiago.springcloud.products.domain.model;

import lombok.Builder;

@Builder
public record Reply<T>(
        String status,
        String message,
        T body
) {}
