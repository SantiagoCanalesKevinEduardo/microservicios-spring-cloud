package com.ksantiago.springcloud.kafka.command.domain.model;

import lombok.Builder;

@Builder
public record Reply<T>(String status, String message, T body) {
}
