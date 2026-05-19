package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper;

import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ClientDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class ClientWebMapperTest {

    private final ClientWebMapper mapper = Mappers.getMapper(ClientWebMapper.class);

    @Test
    void toDomain_ShouldMapDtoToDomain() {
        // Arrange
        ClientDto dto = ClientDto.builder()
                .id(1L)
                .name("Santiago")
                .lastName("Canales")
                .age(25)
                .build();

        // Act
        Client domain = mapper.toDomain(dto);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Santiago");
        assertThat(domain.getLastName()).isEqualTo("Canales");
        assertThat(domain.getAge()).isEqualTo(25);
    }

    @Test
    void toDomain_WhenDtoIsNull_ShouldReturnNull() {
        // Act
        Client domain = mapper.toDomain(null);

        // Assert
        assertThat(domain).isNull();
    }

    @Test
    void toDto_ShouldMapDomainToDto() {
        // Arrange
        Client domain = Client.builder()
                .id(2L)
                .name("Kevin")
                .lastName("Eduardo")
                .age(30)
                .build();

        // Act
        ClientDto dto = mapper.toDto(domain);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(2L);
        assertThat(dto.name()).isEqualTo("Kevin");
        assertThat(dto.lastName()).isEqualTo("Eduardo");
        assertThat(dto.age()).isEqualTo(30);
    }

    @Test
    void toDto_WhenDomainIsNull_ShouldReturnNull() {
        // Act
        ClientDto dto = mapper.toDto(null);

        // Assert
        assertThat(dto).isNull();
    }
}
