package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka;

import com.ksantiago.springcloud.kafka.command.application.port.in.ClientUseCase;
import com.ksantiago.springcloud.kafka.command.domain.model.Client;
import com.ksantiago.springcloud.kafka.command.domain.model.Command;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ClientDto;
import com.ksantiago.springcloud.kafka.command.infrastructure.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientCommandConsumer {

    private final ClientUseCase clientUseCase;
    private final ClientMapper clientMapper;

    @Bean
    public Consumer<Command<ClientDto>> handleClients(){

        return command-> {
            String type= command.type() == null ? "" : command.type().toUpperCase();
            switch (type){
                case "CREATE" ->{
                    if(command.body() == null ){
                        log.warn("Empty body");
                        return;
                    }
                    Client clientDomain = clientMapper.toDomain(command.body());
                    Client savedClient = clientUseCase.create(clientDomain);
                    ClientDto clientDto = clientMapper.toDto(savedClient);
                    log.info("Creating client id= {}, name= {}, lastName = {}, age = {}",clientDto.id(), clientDto.name(), clientDto.lastName(), clientDto.age());
                }
                default -> { log.info("No se encontro ningun command de relevancia");}
            }
        };
    }
}
