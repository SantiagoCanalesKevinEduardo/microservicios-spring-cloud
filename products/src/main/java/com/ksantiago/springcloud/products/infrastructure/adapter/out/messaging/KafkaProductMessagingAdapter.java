package com.ksantiago.springcloud.products.infrastructure.adapter.out.messaging;

import com.ksantiago.springcloud.products.application.port.out.ProductMessagingPort;
import com.ksantiago.springcloud.products.domain.model.Command;
import com.ksantiago.springcloud.products.domain.model.Reply;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.messaging.ReplyInbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class KafkaProductMessagingAdapter implements ProductMessagingPort {

    private final StreamBridge streamBridge;
    private final ReplyInbox replyInbox;
    private static final String BINDING_NAME = "commands-out-0";

    public KafkaProductMessagingAdapter(StreamBridge streamBridge, ReplyInbox replyInbox) {
        this.streamBridge = streamBridge;
        this.replyInbox = replyInbox;
    }

    @Override
    public Reply<?> sendCommandAndAwaitReply(Command<?> command, Duration timeout) {
        return executeCommand(command, timeout);
    }

    private Reply<?> executeCommand(Command<?> command, Duration timeout) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Reply<?>> future = replyInbox.registerRequest(correlationId);

        Message<Command<?>> message = MessageBuilder.<Command<?>>withPayload(command)
                .setHeader("correlationId", correlationId)
                .build();

        log.info("Sending command of type {} with correlationId {}", command.type(), correlationId);

        boolean sent = streamBridge.send(BINDING_NAME, message);
        if (!sent) {
            log.error("Failed to send command to Kafka topic for correlationId {}", correlationId);
            replyInbox.complete(correlationId, null); // Cleanup
            return Reply.builder().status("ERROR").message("Failed to send command to messaging system").build();
        }

        try {
            return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for reply, correlationId: {}", correlationId);
            return Reply.builder().status("ERROR").message("Interrupted waiting for response").build();
        } catch (ExecutionException e) {
            log.error("Execution error while waiting for reply, correlationId: {}", correlationId, e);
            return Reply.builder().status("ERROR").message("Error executing request").build();
        } catch (TimeoutException e) {
            log.warn("Timeout waiting for reply, correlationId: {}", correlationId);
            return Reply.builder().status("ERROR").message("Timeout waiting for response").build();
        } finally {
            replyInbox.complete(correlationId, null); // Cleanup in case of error
        }
    }
}
