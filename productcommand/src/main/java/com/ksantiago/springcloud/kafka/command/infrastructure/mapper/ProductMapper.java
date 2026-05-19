package com.ksantiago.springcloud.kafka.command.infrastructure.mapper;

import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ProductJpaEntity;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    Product toDomain(ProductJpaEntity entity);
    ProductJpaEntity toEntity(Product domain);
    
    Product toDomain(ProductDto dto);
    ProductDto toDto(Product domain);
}
