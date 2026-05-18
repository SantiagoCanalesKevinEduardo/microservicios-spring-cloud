package com.ksantiago.springcloud.products.models;

import lombok.Builder;

@Builder
public record Reply<T>(String status, String message, T body) {

}
