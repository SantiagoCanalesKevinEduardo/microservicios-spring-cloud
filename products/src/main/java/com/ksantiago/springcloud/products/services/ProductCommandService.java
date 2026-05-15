package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.dto.ProductDto;

public interface ProductCommandService {

    void sendCreate(ProductDto productDto);

}
