package com.ksantiago.springcloud.products.infrastructure.adapter.in.messaging;

import com.ksantiago.springcloud.products.domain.model.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class ReplyConsumer {

    private final ReplyInbox replyInbox;

    public ReplyConsumer(ReplyInbox replyInbox) {
        this.replyInbox = replyInbox;
    }

    @Bean
    public Consumer<Message<Reply<?>>> handlerReplies() {
        return message -> {
            String correlationId = message.getHeaders().get("correlationId", String.class);
            if (correlationId != null) {
                log.debug("Received reply for correlationId: {}", correlationId);
                replyInbox.complete(correlationId, message.getPayload());
            } else {
                log.warn("Received reply without correlationId. Payload: {}", message.getPayload());
            }
        };
    }
}
