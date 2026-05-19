package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto;

import lombok.Builder;

@Builder
public record ProductDto(Long id, String name, Double price) {
}
