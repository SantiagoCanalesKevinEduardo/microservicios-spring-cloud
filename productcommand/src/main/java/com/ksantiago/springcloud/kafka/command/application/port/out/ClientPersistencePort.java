package com.ksantiago.springcloud.kafka.command.application.port.out;

import com.ksantiago.springcloud.kafka.command.domain.model.Client;

public interface ClientPersistencePort {
    Client save(Client client);
}
