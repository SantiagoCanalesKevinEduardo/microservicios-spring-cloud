package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.messaging.ReplyInbox;
import com.ksantiago.springcloud.products.models.Command;
import com.ksantiago.springcloud.products.models.Reply;
import com.ksantiago.springcloud.products.models.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCommandServiceImpl implements ProductCommandService{

    private final StreamBridge bridge;
    private final ReplyInbox replyInbox;

    @Override
    public Reply<?> sendCreateAndAwait(ProductDto productDto, Duration timeout) {

        var cmd = Command.<ProductDto>builder().type("CREATE")
                .body(productDto).build();

        return sendAndAwait(cmd, timeout);
    }

    @Override
    public Reply<?> sendReadAndAwait(Long id, Duration timeout) {
        var cmd = new Command<>("READ", id,null);
        return sendAndAwait(cmd, timeout);
    }

    @Override
    public Reply<?> sendReadAllAndAwait(Duration timeout) {
        var cmd = new Command<>("READ ALL", null,null);
        return sendAndAwait(cmd, timeout);
    }

    @Override
    public Reply<?> sendDeleteAndAwait(Long id, Duration timeout) {
        var cmd = new Command("DELETE", id, null);
        return sendAndAwait(cmd,timeout);    }

    @Override
    public Reply<?> sendUpdateAndAwait(Long id, ProductDto productDto, Duration timeout) {
        var cmd = new Command("UPDATE", id, productDto);
        return sendAndAwait(cmd,timeout);
    }

    private Reply<?> sendAndAwait(Command<?> cmd, Duration timeout){
        String correlationId = UUID.randomUUID().toString();

        log.info("Correlation id : {}", correlationId);
        CompletableFuture<Reply<?>> future = replyInbox.register(correlationId);

        var msg = MessageBuilder.withPayload(cmd)
                .setHeader("correlationId", correlationId).build();
        boolean isSend = bridge.send("commands-out-0", msg);
        if(!isSend){
            throw new IllegalStateException("No se pudo enviar el commando a kafka");
        }

        try {
            return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Timeout eesperando repsueta de products-command desde kafka",e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
