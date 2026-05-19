package com.ksantiago.springcloud.kafka.command.domain.model;

public record Command<T>(
        String type,
        Long id,
        T body
) {
}
