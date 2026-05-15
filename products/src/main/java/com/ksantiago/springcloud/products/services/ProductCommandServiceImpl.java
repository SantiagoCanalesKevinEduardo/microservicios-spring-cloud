package com.ksantiago.springcloud.products.services;

import com.ksantiago.springcloud.products.models.Command;
import com.ksantiago.springcloud.products.models.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService{

    private final StreamBridge bridge;

    @Override
    public void sendCreate(ProductDto productDto) {
        Command<ProductDto> cmd = Command.<ProductDto>builder().type("CREATE")
                .body(productDto).build();

        boolean isSend = bridge.send("commands-out-0", cmd);

        if(!isSend){
            throw new IllegalStateException("No se pudo enviar el commando a kafka");
        }
    }
}
