package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper;

import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductWebMapperTest {

    private final ProductWebMapper mapper = Mappers.getMapper(ProductWebMapper.class);

    @Test
    void toDomain_ShouldMapDtoToDomain() {
        // Arrange
        ProductDto dto = ProductDto.builder()
                .id(1L)
                .name("Keyboard")
                .price(50.0)
                .build();

        // Act
        Product domain = mapper.toDomain(dto);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Keyboard");
        assertThat(domain.getPrice()).isEqualTo(50.0);
    }

    @Test
    void toDomain_WhenDtoIsNull_ShouldReturnNull() {
        // Act
        Product domain = mapper.toDomain(null);

        // Assert
        assertThat(domain).isNull();
    }

    @Test
    void toDto_ShouldMapDomainToDto() {
        // Arrange
        Product domain = Product.builder()
                .id(2L)
                .name("Mouse")
                .price(25.0)
                .build();

        // Act
        ProductDto dto = mapper.toDto(domain);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(2L);
        assertThat(dto.name()).isEqualTo("Mouse");
        assertThat(dto.price()).isEqualTo(25.0);
    }

    @Test
    void toDto_WhenDomainIsNull_ShouldReturnNull() {
        // Act
        ProductDto dto = mapper.toDto(null);

        // Assert
        assertThat(dto).isNull();
    }

    @Test
    void toDtoList_ShouldMapDomainListToDtoList() {
        // Arrange
        Product domain1 = Product.builder().id(1L).name("Monitor").price(200.0).build();
        Product domain2 = Product.builder().id(2L).name("Cable").price(10.0).build();
        List<Product> domainList = List.of(domain1, domain2);

        // Act
        List<ProductDto> dtoList = mapper.toDtoList(domainList);

        // Assert
        assertThat(dtoList).isNotNull().hasSize(2);
        assertThat(dtoList.get(0).name()).isEqualTo("Monitor");
        assertThat(dtoList.get(1).name()).isEqualTo("Cable");
    }
}
