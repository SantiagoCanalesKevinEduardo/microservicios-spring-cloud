package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.Reply;
import com.ksantiago.springcloud.products.models.dto.ProductDto;

import java.time.Duration;

public interface ProductCommandService {

    Reply<?> sendCreateAndAwait(ProductDto productDto, Duration timeout);
    Reply<?> sendReadAndAwait(Long id, Duration timeout);
    Reply<?> sendReadAllAndAwait(Duration timeout);
    Reply<?> sendDeleteAndAwait(Long id, Duration timeout);
    Reply<?> sendUpdateAndAwait(Long id,ProductDto productDto, Duration timeout);

}
