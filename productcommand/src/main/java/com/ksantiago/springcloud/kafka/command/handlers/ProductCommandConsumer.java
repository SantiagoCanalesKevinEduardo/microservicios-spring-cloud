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

import java.util.List;
import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ProductCommandConsumer {

    private final ProductService productService;

    @Bean
    public Function<Message<Command<ProductDto>>,Message <Reply<?>>>  handlerCommands() {
        return msg -> {
            Command<ProductDto> command = msg.getPayload();
            String type = command.type() == null ? "" : command.type().toUpperCase();
            Reply<?> respuesta = null;
            switch (type) {
                case "CREATE" -> {
                    if (command.body() == null) {
                        log.warn("Empty body for CREATE command");
                        respuesta = Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Empty body")
                                .build();
                    }else{

                        ProductDto productDto = productService.create(command.body());
                        log.info("Creating product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                        respuesta = Reply.<ProductDto>builder()
                                .status("SUCCESS")
                                .message("Product created")
                                .body(productDto)
                                .build();
                    }
                } case "READ" -> {
                    if (command.id() == null) {
                        log.warn("Empty id for READ command");
                        respuesta = Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Id is required")
                                .build();
                    }else{
                        ProductDto productDto = productService.findById(command.id());
                        log.info("Reading product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                        respuesta = (productDto==null) ?
                                Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Product not found")
                                .build() :
                                Reply.<ProductDto>builder()
                                .status("SUCCESS")
                                .message("Product read")
                                .body(productDto)
                                .build();
                    }
                }case "READ ALL" -> {
                   List<ProductDto> productsDto = productService.findAll();
                        respuesta =Reply.<List<ProductDto>>builder()
                                .status("SUCCESS")
                                .message("Product read")
                                .body(productsDto)
                                .build();

                }
                case "UPDATE" -> {
                    if (command.body() == null || command.id() == null) {
                        log.warn("Empty body or id for UPDATE command");
                        respuesta = Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Empty body or id")
                                .build();
                    }else{

                        ProductDto productDto = productService.update(command.id(), command.body());
                        log.info("Updated product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                        respuesta = Reply.<ProductDto>builder()
                                .status("SUCCESS")
                                .message("Product updated")
                                .body(productDto)
                                .build();
                    }
                }
                case "DELETE" -> {
                    if(command.id() ==null){
                        log.warn("Empty id for DELETE command");
                        respuesta = Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Product not deleted")
                                .build();
                    }else {
                        boolean deleted = productService.delete(command.id());
                        log.info("Deleted product id= {}", command.id());
                        if(!deleted){
                            respuesta = Reply.<ProductDto>builder()
                                    .status("ERROR")
                                    .message("Product not deleted")
                                    .build();
                        }else{
                            respuesta = Reply.<ProductDto>builder()
                                    .status("SUCCESS")
                                    .message("Product deleted")
                                    .build();
                        }
                    }
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

            MessageBuilder<Reply<?>> out = MessageBuilder.withPayload(respuesta);
            if(correlationId!=null && !correlationId.isEmpty()){
                out.setHeader("correlationId", correlationId);
            }

            return out.build();
        };
    }
}
