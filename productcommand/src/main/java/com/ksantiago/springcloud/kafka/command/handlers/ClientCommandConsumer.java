package com.ksantiago.springcloud.kafka.command.handlers;

import com.ksantiago.springcloud.kafka.command.models.Command;
import com.ksantiago.springcloud.kafka.command.models.dto.ClientDto;
import com.ksantiago.springcloud.kafka.command.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientCommandConsumer {

    private final ClientService clientService;

    @Bean
    public Consumer<Command<ClientDto>> handleClients(){

        return command-> {
            String type= command.type() == null ? "" : command.type().toUpperCase();
            switch (type){
                case "CREATE" ->{
                    if(command.body() == null ){
                        log.warn("Empty body");
                        return;
                    }
                    ClientDto clientDto = clientService.create(command.body());
                    log.info("Creating client id= {}, name= {}, lastName = {}, age = {}",clientDto.id(), clientDto.name(), clientDto.lastName(), clientDto.age());
                }
                default -> { log.info("No se encontro ningun command de relevancia");}
            }
        };
    }
    /*
    *  public Consumer<Command<ProductDto>> handlerCommands(ProductService productService){



        return command->{
           String type= command.type() == null ? "" : command.type().toUpperCase();

           switch (type){
               case "CREATE" ->{
                   if(command.body() == null ){
                       log.warn("Empty body");
                       return;
                   }
                   ProductDto productDto = productService.create(command.body());
                   log.info("Creating product id= {}, name= {}, price = {}",productDto.id(), productDto.name(), productDto.price());
               }
               case "UPDATE"->{
                   log.info("Updating product name= {}, price = {}", command.body().name(), command.body().price());
               }
               case "DELETE"->{
                   log.info("Deleting product");
               }
               default -> {}
           }

        };
    }*/

}
