package com.ksantiago.springcloud.kafka.command.application.port.in;

import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import java.util.List;

public interface ProductUseCase {
    Product create(Product product);
    Product findById(Long id);
    List<Product> findAll();
    Product update(Long id, Product product);
    boolean delete(Long id);
}
