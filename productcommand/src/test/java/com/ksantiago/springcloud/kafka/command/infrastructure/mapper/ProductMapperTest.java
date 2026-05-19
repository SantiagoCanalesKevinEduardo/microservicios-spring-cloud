package com.ksantiago.springcloud.kafka.command.infrastructure.mapper;

import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ProductJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toDomain_FromEntity_ShouldMapCorrectly() {
        // Arrange
        ProductJpaEntity entity = new ProductJpaEntity();
        entity.setId(1L);
        entity.setName("Entity Product");
        entity.setPrice(99.9);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Product domain = mapper.toDomain(entity);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Entity Product");
        assertThat(domain.getPrice()).isEqualTo(99.9);
    }

    @Test
    void toEntity_FromDomain_ShouldMapCorrectly() {
        // Arrange
        Product domain = Product.builder()
                .id(2L)
                .name("Domain Product")
                .price(150.0)
                .build();

        // Act
        ProductJpaEntity entity = mapper.toEntity(domain);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getName()).isEqualTo("Domain Product");
        assertThat(entity.getPrice()).isEqualTo(150.0);
        // createdAt and updatedAt are null until persisted
    }

    @Test
    void toDomain_FromDto_ShouldMapCorrectly() {
        // Arrange
        ProductDto dto = ProductDto.builder()
                .id(3L)
                .name("Dto Product")
                .price(200.0)
                .build();

        // Act
        Product domain = mapper.toDomain(dto);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(3L);
        assertThat(domain.getName()).isEqualTo("Dto Product");
        assertThat(domain.getPrice()).isEqualTo(200.0);
    }

    @Test
    void toDto_FromDomain_ShouldMapCorrectly() {
        // Arrange
        Product domain = Product.builder()
                .id(4L)
                .name("Domain Product 2")
                .price(250.0)
                .build();

        // Act
        ProductDto dto = mapper.toDto(domain);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(4L);
        assertThat(dto.name()).isEqualTo("Domain Product 2");
        assertThat(dto.price()).isEqualTo(250.0);
    }

    @Test
    void nullInputs_ShouldReturnNull() {
        assertThat(mapper.toDomain((ProductJpaEntity) null)).isNull();
        assertThat(mapper.toEntity((Product) null)).isNull();
        assertThat(mapper.toDomain((ProductDto) null)).isNull();
        assertThat(mapper.toDto((Product) null)).isNull();
    }
}
