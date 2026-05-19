package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create (ProductDto dto);
    ProductDto findById(Long id);
    List<ProductDto> findAll();
    ProductDto update(Long id, ProductDto productDto);
    boolean delete(Long id);
}
