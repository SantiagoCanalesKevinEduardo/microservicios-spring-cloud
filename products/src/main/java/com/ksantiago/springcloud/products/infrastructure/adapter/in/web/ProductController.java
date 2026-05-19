package com.ksantiago.springcloud.products.infrastructure.adapter.in.web;

import com.ksantiago.springcloud.products.application.port.in.ProductUseCase;
import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ProductDto;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper.ProductWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductWebMapper productMapper;

    public ProductController(ProductUseCase productUseCase, ProductWebMapper productMapper) {
        this.productUseCase = productUseCase;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.toDomain(productDto);
        Reply<Product> reply = productUseCase.createProduct(product);
        return mapToResponseEntity(reply);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Reply<Product> reply = productUseCase.getProductById(id);
        return mapToResponseEntity(reply);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        Reply<List<Product>> reply = productUseCase.getAllProducts();
        if ("SUCCESS".equalsIgnoreCase(reply.status())) {
            List<ProductDto> dtos = productMapper.toDtoList(reply.body());
            return ResponseEntity.ok().body(dtos);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", reply.message()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.toDomain(productDto);
        Reply<Product> reply = productUseCase.updateProduct(id, product);
        return mapToResponseEntity(reply);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Reply<Product> reply = productUseCase.deleteProduct(id);
        return mapToResponseEntity(reply);
    }

    private ResponseEntity<?> mapToResponseEntity(Reply<Product> reply) {
        if ("SUCCESS".equalsIgnoreCase(reply.status())) {
            return ResponseEntity.ok().body(productMapper.toDto(reply.body()));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", reply.message()));
        }
    }
}
