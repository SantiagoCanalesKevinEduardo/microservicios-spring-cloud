package com.ksantiago.springcloud.kafka.command.infrastructure.mapper;

import com.ksantiago.springcloud.kafka.command.domain.model.Client;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ClientDto;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ClientJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClientMapperTest {

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void toDomain_FromEntity_ShouldMapCorrectly() {
        // Arrange
        ClientJpaEntity entity = new ClientJpaEntity();
        entity.setId(1L);
        entity.setName("Santiago");
        entity.setLastName("Canales");
        entity.setAge(25);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Client domain = mapper.toDomain(entity);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Santiago");
        assertThat(domain.getLastName()).isEqualTo("Canales");
        assertThat(domain.getAge()).isEqualTo(25);
    }

    @Test
    void toEntity_FromDomain_ShouldMapCorrectly() {
        // Arrange
        Client domain = Client.builder()
                .id(2L)
                .name("Kevin")
                .lastName("Eduardo")
                .age(30)
                .build();

        // Act
        ClientJpaEntity entity = mapper.toEntity(domain);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getName()).isEqualTo("Kevin");
        assertThat(entity.getLastName()).isEqualTo("Eduardo");
        assertThat(entity.getAge()).isEqualTo(30);
    }

    @Test
    void toDomain_FromDto_ShouldMapCorrectly() {
        // Arrange
        ClientDto dto = ClientDto.builder()
                .id(3L)
                .name("Dto Client")
                .lastName("Lastname")
                .age(40)
                .build();

        // Act
        Client domain = mapper.toDomain(dto);

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(3L);
        assertThat(domain.getName()).isEqualTo("Dto Client");
        assertThat(domain.getLastName()).isEqualTo("Lastname");
        assertThat(domain.getAge()).isEqualTo(40);
    }

    @Test
    void toDto_FromDomain_ShouldMapCorrectly() {
        // Arrange
        Client domain = Client.builder()
                .id(4L)
                .name("Domain Client")
                .lastName("Another")
                .age(50)
                .build();

        // Act
        ClientDto dto = mapper.toDto(domain);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(4L);
        assertThat(dto.name()).isEqualTo("Domain Client");
        assertThat(dto.lastName()).isEqualTo("Another");
        assertThat(dto.age()).isEqualTo(50);
    }

    @Test
    void nullInputs_ShouldReturnNull() {
        assertThat(mapper.toDomain((ClientJpaEntity) null)).isNull();
        assertThat(mapper.toEntity((Client) null)).isNull();
        assertThat(mapper.toDomain((ClientDto) null)).isNull();
        assertThat(mapper.toDto((Client) null)).isNull();
    }
}
