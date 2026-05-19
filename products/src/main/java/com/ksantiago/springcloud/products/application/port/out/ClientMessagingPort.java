package com.ksantiago.springcloud.products.application.port.out;

import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.domain.model.Command;

public interface ClientMessagingPort {
    void sendCommand(Command<Client> command);
}
