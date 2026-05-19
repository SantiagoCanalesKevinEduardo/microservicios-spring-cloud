package com.ksantiago.springcloud.products.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Client {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
}
