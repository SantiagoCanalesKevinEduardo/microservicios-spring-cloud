package com.ksantiago.springcloud.products.application.port.in;

import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;

import java.util.List;

public interface ProductUseCase {
    Reply<Product> createProduct(Product product);
    Reply<Product> getProductById(Long id);
    Reply<List<Product>> getAllProducts();
    Reply<Product> updateProduct(Long id, Product product);
    Reply<Product> deleteProduct(Long id);
}
