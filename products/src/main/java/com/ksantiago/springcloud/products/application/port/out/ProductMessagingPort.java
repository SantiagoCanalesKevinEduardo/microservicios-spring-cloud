package com.ksantiago.springcloud.products.application.port.out;

import com.ksantiago.springcloud.products.domain.model.Command;
import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;

import java.time.Duration;

public interface ProductMessagingPort {
    Reply<?> sendCommandAndAwaitReply(Command<?> command, Duration timeout);
}
