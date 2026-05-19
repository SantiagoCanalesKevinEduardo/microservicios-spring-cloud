package com.ksantiago.springcloud.products.domain.model;

import lombok.Builder;

@Builder
public record Command<T>(
        String type,
        Long id,
        T body
) {}
