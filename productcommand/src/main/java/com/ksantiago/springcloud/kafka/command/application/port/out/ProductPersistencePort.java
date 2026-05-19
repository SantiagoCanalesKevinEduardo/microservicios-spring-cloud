package com.ksantiago.springcloud.kafka.command.application.port.out;

import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void delete(Product product);
}
