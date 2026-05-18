package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.dto.ClientDto;

public interface ClientCommandService {

    void sendCreate(ClientDto clientDto);


}
