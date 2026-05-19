package com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper;

import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientWebMapper {
    Client toDomain(ClientDto dto);
    ClientDto toDto(Client domain);
}
