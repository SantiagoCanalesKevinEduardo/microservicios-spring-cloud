package com.ksantiago.springcloud.products.application.port.in;

import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.domain.model.Reply;

public interface ClientUseCase {
    void createClient(Client client);
}
