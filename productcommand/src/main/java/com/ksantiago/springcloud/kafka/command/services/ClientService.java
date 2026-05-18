package com.ksantiago.springcloud.kafka.command.services;

import com.ksantiago.springcloud.kafka.command.models.dto.ClientDto;

public interface ClientService {

    ClientDto create(ClientDto dto);
}
