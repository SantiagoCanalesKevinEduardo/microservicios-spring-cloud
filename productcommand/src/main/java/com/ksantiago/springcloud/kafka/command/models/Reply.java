package com.ksantiago.springcloud.kafka.command.models;

import lombok.Builder;

@Builder
public record Reply<T>(String status, String message, T body) {

}

/*package com.ksantiago.springcloud.kafka.command.models;

import lombok.Builder;

@Builder
public record Reply<T>(String status, String message, T body) {
}
*/