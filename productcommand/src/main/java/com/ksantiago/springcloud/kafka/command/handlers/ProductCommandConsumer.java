package com.ksantiago.springcloud.kafka.command.handlers;

import com.ksantiago.springcloud.kafka.command.models.Command;
import com.ksantiago.springcloud.kafka.command.models.Reply;
import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;
import com.ksantiago.springcloud.kafka.command.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ProductCommandConsumer {

    private final ProductService productService;

    @Bean
    public Function<Command<ProductDto>, Reply<ProductDto>> handlerCommands() {
        return command -> {
            String type = command.type() == null ? "" : command.type().toUpperCase();

            switch (type) {
                case "CREATE" -> {
                    if (command.body() == null) {
                        log.warn("Empty body for CREATE command");
                        return Reply.<ProductDto>builder()
                                .status("ERROR")
                                .message("Empty body")
                                .build();
                    }
                    ProductDto productDto = productService.create(command.body());
                    log.info("Creating product id= {}, name= {}, price = {}", productDto.id(), productDto.name(), productDto.price());
                    return Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Product created")
                            .body(productDto)
                            .build();
                }
                case "UPDATE" -> {
                    log.info("Updating product name= {}, price = {}", command.body().name(), command.body().price());
                    return Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Update logic not fully implemented")
                            .build();
                }
                case "DELETE" -> {
                    log.info("Deleting product");
                    return Reply.<ProductDto>builder()
                            .status("SUCCESS")
                            .message("Delete logic not fully implemented")
                            .build();
                }
                default -> {
                    log.warn("Unknown command type: {}", type);
                    return Reply.<ProductDto>builder()
                            .status("ERROR")
                            .message("Unknown command type")
                            .build();
                }
            }
        };
    }
}
