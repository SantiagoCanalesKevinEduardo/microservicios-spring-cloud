package com.ksantiago.springcloud.products.infrastructure.adapter.out.messaging;

import com.ksantiago.springcloud.products.application.port.out.ClientMessagingPort;
import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.domain.model.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaClientMessagingAdapter implements ClientMessagingPort {

    private final StreamBridge streamBridge;
    private static final String BINDING_NAME = "clients-out-0";

    public KafkaClientMessagingAdapter(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void sendCommand(Command<Client> command) {
        String correlationId = UUID.randomUUID().toString();
        Message<Command<Client>> message = MessageBuilder.withPayload(command)
                .setHeader("correlationId", correlationId)
                .build();
        
        log.info("Sending client command of type {} with correlationId {}", command.type(), correlationId);
        
        boolean sent = streamBridge.send(BINDING_NAME, message);
        if (!sent) {
            log.error("Failed to send client command to Kafka topic for correlationId {}", correlationId);
        }
    }
}
