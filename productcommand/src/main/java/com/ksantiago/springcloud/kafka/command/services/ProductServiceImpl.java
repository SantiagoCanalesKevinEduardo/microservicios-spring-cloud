package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.entities.Product;
import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        Product product = new Product(dto.name(),dto.price());
        Product productSave= productRepository.save(product) ;
        return ProductDto.builder()
                .id(productSave.getId())
                .name(productSave.getName())
                .price(product.getPrice())
                .build();
    }
}
