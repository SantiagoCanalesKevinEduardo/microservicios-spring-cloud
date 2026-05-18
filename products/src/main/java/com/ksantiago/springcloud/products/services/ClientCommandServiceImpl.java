package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.Command;
import com.ksantiago.springcloud.products.models.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final StreamBridge bridge;

    @Override
    public void sendCreate(ClientDto dto) {
        Command<ClientDto> cmd = Command.<ClientDto>builder().type("CREATE")
                .body(dto).build();

        boolean isSend = bridge.send("clients-out-0", dto);

        if(!isSend){
            throw new IllegalStateException("No se pudo enviar el commando de cliente a kafka");
        }


    }
}


