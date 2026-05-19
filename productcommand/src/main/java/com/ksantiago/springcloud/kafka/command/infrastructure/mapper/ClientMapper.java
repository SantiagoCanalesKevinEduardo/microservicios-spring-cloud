package com.ksantiago.springcloud.kafka.command.infrastructure.mapper;

import com.ksantiago.springcloud.kafka.command.domain.model.Client;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ClientJpaEntity;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    Client toDomain(ClientJpaEntity entity);
    ClientJpaEntity toEntity(Client domain);
    
    Client toDomain(ClientDto dto);
    ClientDto toDto(Client domain);
}
