package com.ksantiago.springcloud.kafka.command.application.port.in;

import com.ksantiago.springcloud.kafka.command.domain.model.Client;

public interface ClientUseCase {
    Client create(Client client);
}
