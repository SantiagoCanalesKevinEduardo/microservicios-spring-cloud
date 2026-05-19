package com.ksantiago.springcloud.products.infrastructure.adapter.in.messaging;

import com.ksantiago.springcloud.products.domain.model.Reply;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReplyInbox {

    private final Map<String, CompletableFuture<Reply<?>>> pendingReplies = new ConcurrentHashMap<>();

    public CompletableFuture<Reply<?>> registerRequest(String correlationId) {
        CompletableFuture<Reply<?>> future = new CompletableFuture<>();
        pendingReplies.put(correlationId, future);
        return future;
    }

    public void complete(String correlationId, Reply<?> reply) {
        CompletableFuture<Reply<?>> future = pendingReplies.remove(correlationId);
        if (future != null) {
            future.complete(reply);
        }
    }
}
