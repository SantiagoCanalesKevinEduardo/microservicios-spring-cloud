package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.dto.ClientDto;

public interface ClientService {

    void sendCreate(ClientDto clientDto);


}
