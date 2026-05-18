package com.ksantiago.springcloud.kafka.command.models.dto;

import lombok.Builder;

@Builder
public record ClientDto(Long id, String name, String lastName, int age) {
}
