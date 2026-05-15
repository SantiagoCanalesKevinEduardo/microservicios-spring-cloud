package com.ksantiago.springcloud.kafka.command.handlers;

import com.ksantiago.springcloud.kafka.command.models.Command;
import com.ksantiago.springcloud.kafka.command.models.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class ProductCommandConsumer {


    @Bean
    public Consumer<Command<ProductDto>> handlerCommands(){
        return Command<ProductDto> command -> {

        };
    }
}
