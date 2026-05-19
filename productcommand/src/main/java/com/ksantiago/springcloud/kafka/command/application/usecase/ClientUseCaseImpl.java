package com.ksantiago.springcloud.kafka.command.application.usecase;

import com.ksantiago.springcloud.kafka.command.application.port.in.ClientUseCase;
import com.ksantiago.springcloud.kafka.command.application.port.out.ClientPersistencePort;
import com.ksantiago.springcloud.kafka.command.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientUseCaseImpl implements ClientUseCase {

    private final ClientPersistencePort clientPersistencePort;

    @Override
    @Transactional
    public Client create(Client client) {
        return clientPersistencePort.save(client);
    }
}
