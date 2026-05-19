package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa;

import com.ksantiago.springcloud.kafka.command.application.port.out.ClientPersistencePort;
import com.ksantiago.springcloud.kafka.command.domain.model.Client;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.entity.ClientJpaEntity;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.out.persistence.jpa.repository.ClientJpaRepository;
import com.ksantiago.springcloud.kafka.command.infrastructure.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements ClientPersistencePort {

    private final ClientJpaRepository repository;
    private final ClientMapper mapper;

    @Override
    public Client save(Client client) {
        ClientJpaEntity entity = mapper.toEntity(client);
        ClientJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
