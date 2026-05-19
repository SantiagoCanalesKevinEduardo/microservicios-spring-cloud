package com.ksantiago.springcloud.kafka.command.application.usecase;

import com.ksantiago.springcloud.kafka.command.application.port.in.ProductUseCase;
import com.ksantiago.springcloud.kafka.command.application.port.out.ProductPersistencePort;
import com.ksantiago.springcloud.kafka.command.domain.exception.ProductNotFoundException;
import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductPersistencePort productPersistencePort;

    @Override
    @Transactional
    public Product create(Product product) {
        return productPersistencePort.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productPersistencePort.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productPersistencePort.findAll();
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) {
        Product existingProduct = productPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        
        return productPersistencePort.save(existingProduct);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Product existingProduct = productPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        productPersistencePort.delete(existingProduct);
        return true;
    }
}
