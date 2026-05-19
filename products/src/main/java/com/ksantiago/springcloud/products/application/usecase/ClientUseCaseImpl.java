package com.ksantiago.springcloud.products.application.usecase;

import com.ksantiago.springcloud.products.application.port.in.ClientUseCase;
import com.ksantiago.springcloud.products.application.port.out.ClientMessagingPort;
import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.domain.model.Command;
import org.springframework.stereotype.Service;

@Service
public class ClientUseCaseImpl implements ClientUseCase {

    private final ClientMessagingPort clientMessagingPort;

    public ClientUseCaseImpl(ClientMessagingPort clientMessagingPort) {
        this.clientMessagingPort = clientMessagingPort;
    }

    @Override
    public void createClient(Client client) {
        Command<Client> command = Command.<Client>builder()
                .type("CREATE")
                .body(client)
                .build();
        clientMessagingPort.sendCommand(command);
    }
}
