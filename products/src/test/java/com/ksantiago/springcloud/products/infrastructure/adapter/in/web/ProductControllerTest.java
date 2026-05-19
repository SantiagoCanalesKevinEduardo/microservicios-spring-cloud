package com.ksantiago.springcloud.products.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksantiago.springcloud.products.application.port.in.ProductUseCase;
import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ProductDto;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper.ProductWebMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductUseCase productUseCase;

    @MockitoBean
    private ProductWebMapper productMapper;

    @Test
    void shouldCreateProductAndReturnSuccess() throws Exception {
        ProductDto productDto = new ProductDto(null, "Laptop", 1200.0);
        Product product = Product.builder().name("Laptop").price(1200.0).build();
        Product savedProduct = Product.builder().id(1L).name("Laptop").price(1200.0).build();
        ProductDto savedProductDto = new ProductDto(1L, "Laptop", 1200.0);
        Reply<Product> reply = new Reply<>("SUCCESS", "Product created", savedProduct);

        when(productMapper.toDomain(any(ProductDto.class))).thenReturn(product);
        when(productUseCase.createProduct(any(Product.class))).thenReturn(reply);
        when(productMapper.toDto(any(Product.class))).thenReturn(savedProductDto);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1200.0));
    }

    @Test
    void shouldReturnBadRequestWhenCreateFails() throws Exception {
        ProductDto productDto = new ProductDto(null, "Laptop", 1200.0);
        Product product = Product.builder().name("Laptop").price(1200.0).build();
        Reply<Product> reply = new Reply<>("ERROR", "Failed to create", null);

        when(productMapper.toDomain(any(ProductDto.class))).thenReturn(product);
        when(productUseCase.createProduct(any(Product.class))).thenReturn(reply);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Failed to create"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        Product product = Product.builder().id(1L).name("Laptop").price(1200.0).build();
        ProductDto productDto = new ProductDto(1L, "Laptop", 1200.0);
        Reply<Product> reply = new Reply<>("SUCCESS", "Found", product);

        when(productUseCase.getProductById(1L)).thenReturn(reply);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        Product product = Product.builder().id(1L).name("Laptop").price(1200.0).build();
        ProductDto productDto = new ProductDto(1L, "Laptop", 1200.0);
        Reply<List<Product>> reply = new Reply<>("SUCCESS", "Found", List.of(product));

        when(productUseCase.getAllProducts()).thenReturn(reply);
        when(productMapper.toDtoList(any())).thenReturn(List.of(productDto));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Laptop Pro", 1500.0);
        Product product = Product.builder().id(1L).name("Laptop Pro").price(1500.0).build();
        Reply<Product> reply = new Reply<>("SUCCESS", "Updated", product);

        when(productMapper.toDomain(any(ProductDto.class))).thenReturn(product);
        when(productUseCase.updateProduct(eq(1L), any(Product.class))).thenReturn(reply);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop Pro"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        Product product = Product.builder().id(1L).name("Laptop").price(1200.0).build();
        ProductDto productDto = new ProductDto(1L, "Laptop", 1200.0);
        Reply<Product> reply = new Reply<>("SUCCESS", "Deleted", product);

        when(productUseCase.deleteProduct(1L)).thenReturn(reply);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() throws Exception {
        ProductDto productDto = new ProductDto(null, "", 1200.0);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest());
    }
}
