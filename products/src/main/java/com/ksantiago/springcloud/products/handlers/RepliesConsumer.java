package com.ksantiago.springcloud.products.handlers;

import com.ksantiago.springcloud.products.messaging.ReplyInbox;
import com.ksantiago.springcloud.products.models.Reply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RepliesConsumer {

    private final ReplyInbox replyInbox;

    @Bean
    public Consumer<Message<Reply<?>>> handlerReplies(){
        return msg -> {

            String correlationID = msg.getHeaders().get("correlationId",String.class);
            replyInbox.complete(correlationID,msg.getPayload());

        };
    }




}
