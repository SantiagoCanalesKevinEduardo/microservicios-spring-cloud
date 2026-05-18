package com.ksantiago.springcloud.kafka.command.handlers;

import com.ksantiago.springcloud.kafka.command.models.Command;
import com.ksantiago.springcloud.kafka.command.models.Reply;
import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ProductCommandConsumer {

    private final ProductService productService;

    @Bean
    public Function<Message<Command<ProductDto>>,Message <Reply<ProductDto>>>  handlerCommands() {
        return msg -> {
            Command<ProductDto> command = msg.getPayload();
            String type = command.type() == null ? "" : command.type().toUpperCase();
            Reply<ProductDto> respuesta = null;
            switch (type) {
                case "CREATE" -> {
                    if (command.body() == null) {
                        log.warn("Empty body for CREATE command");
                        respuesta = Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Empty body")
                                .build();
                    }
                    ProductDto productDto = productService.create(command.body());
                    log.info("Creating product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                    respuesta = Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Product created")
                            .body(productDto)
                            .build();
                }
                case "UPDATE" -> {
                    log.info("Updating product name= {}, price = {}", command.body().name(), command.body().price());
                    respuesta =  Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Update logic not fully implemented")
                            .build();
                }
                case "DELETE" -> {
                    log.info("Deleting product");
                    respuesta =  Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Delete logic not fully implemented")
                            .build();
                }
                default -> {
                    log.warn("Unknown command type: {}", type);
                    respuesta =  Reply.<ProductDto>builder()
                            .status("ERROR")
                            .message("Unknown command type")
                            .build();
                }
            }
            String correlationId = msg.getHeaders().get("correlationId", String.class);
            log.info("El correlationId es {}", correlationId);

            MessageBuilder<Reply<ProductDto>> out = MessageBuilder.withPayload(respuesta);
            if(correlationId!=null && !correlationId.isEmpty()){
                out.setHeader("correlationId", correlationId);
            }

            return out.build();
        };
    }
}
