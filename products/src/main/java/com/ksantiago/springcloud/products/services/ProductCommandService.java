package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.Reply;
import com.ksantiago.springcloud.products.models.dto.ProductDto;

import java.time.Duration;

public interface ProductCommandService {

    Reply<?> sendCreateAndAwait(ProductDto productDto, Duration timeout);

}
