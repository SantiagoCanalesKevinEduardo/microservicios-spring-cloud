package com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka;

import com.ksantiago.springcloud.kafka.command.application.port.in.ProductUseCase;
import com.ksantiago.springcloud.kafka.command.domain.model.Command;
import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import com.ksantiago.springcloud.kafka.command.domain.model.Reply;
import com.ksantiago.springcloud.kafka.command.infrastructure.adapter.in.kafka.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.infrastructure.mapper.ProductMapper;
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

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

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
                        Product productDomain = productMapper.toDomain(command.body());
                        Product savedProduct = productUseCase.create(productDomain);
                        ProductDto productDto = productMapper.toDto(savedProduct);
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
                        Product product = productUseCase.findById(command.id());
                        ProductDto productDto = productMapper.toDto(product);
                        if (productDto != null) {
                            log.info("Reading product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                            respuesta = Reply.<ProductDto>builder()
                                    .status("SUCCESS")
                                    .message("Product read")
                                    .body(productDto)
                                    .build();
                        } else {
                            respuesta = Reply.<ProductDto>builder()
                                    .status("ERROR")
                                    .message("Product not found")
                                    .build();
                        }
                    }
                }case "READ ALL" -> {
                   List<Product> products = productUseCase.findAll();
                   List<ProductDto> productsDto = products.stream().map(productMapper::toDto).toList();
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
                        Product productDomain = productMapper.toDomain(command.body());
                        Product updatedProduct = productUseCase.update(command.id(), productDomain);
                        ProductDto productDto = productMapper.toDto(updatedProduct);
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
                        boolean deleted = productUseCase.delete(command.id());
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
