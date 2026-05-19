package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper;

import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {
    Product toDomain(ProductDto dto);
    ProductDto toDto(Product domain);
    List<ProductDto> toDtoList(List<Product> domainList);
}
