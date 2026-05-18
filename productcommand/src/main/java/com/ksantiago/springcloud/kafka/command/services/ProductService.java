package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;

public interface ProductService {
    ProductDto create (ProductDto dto);
}
