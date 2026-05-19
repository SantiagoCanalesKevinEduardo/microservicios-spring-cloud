package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.entities.Product;
import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        Product product = new Product(dto.name(),dto.price());
        Product productSave= productRepository.save(product) ;
        return toDto(productSave);
    }


    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(()-> new FindException("No se encontro product con este id"));
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        return toDto(productRepository.save(product));
    }

    @Override
    public boolean delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new FindException("No se encontro product con este id"));
        productRepository.delete(product);
        return true;
    }

    private ProductDto toDto(Product product){
        return  ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
