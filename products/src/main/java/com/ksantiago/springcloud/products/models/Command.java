package com.ksantiago.springcloud.products.models;

import lombok.Builder;

@Builder
public record Command<T>(
        String type,
        Long id,
        T body
) { }
