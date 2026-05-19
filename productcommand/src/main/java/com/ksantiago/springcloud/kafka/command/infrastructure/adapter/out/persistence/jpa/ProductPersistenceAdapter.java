package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa;

import com.ksantiago.springcloud.kafka.command.application.port.out.ProductPersistencePort;
import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ProductJpaEntity;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.repository.ProductJpaRepository;
import com.ksantiago.springcloud.kafka.command.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductJpaRepository repository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = mapper.toEntity(product);
        ProductJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Product product) {
        ProductJpaEntity entity = mapper.toEntity(product);
        repository.delete(entity);
    }
}
