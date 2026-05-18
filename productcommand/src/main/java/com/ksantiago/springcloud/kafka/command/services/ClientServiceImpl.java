package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.entities.Client;
import com.ksantiago.springcloud.kafka.command.models.dto.ClientDto;
import com.ksantiago.springcloud.kafka.command.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public ClientDto create(ClientDto dto) {
        Client client = new Client(dto.name(),dto.lastName(),dto.age());
        Client clientSave = clientRepository.save(client);

        return ClientDto.builder()
                .id(clientSave.getId())
                .name(clientSave.getName())
                .lastName(clientSave.getLastName())
                .age(clientSave.getAge())
                .build();
    }
}
