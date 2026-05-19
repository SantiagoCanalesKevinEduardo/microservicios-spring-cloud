package com.ksantiago.springcloud.products.application.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksantiago.springcloud.products.application.port.in.ProductUseCase;
import com.ksantiago.springcloud.products.application.port.out.ProductMessagingPort;
import com.ksantiago.springcloud.products.domain.model.Command;
import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductMessagingPort productMessagingPort;
    private final ObjectMapper objectMapper;
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    public ProductUseCaseImpl(ProductMessagingPort productMessagingPort, ObjectMapper objectMapper) {
        this.productMessagingPort = productMessagingPort;
        this.objectMapper = objectMapper;
    }

    @Override
    public Reply<Product> createProduct(Product product) {
        Command<Product> command = Command.<Product>builder()
                .type("CREATE")
                .body(product)
                .build();
        
        Reply<?> rawReply = productMessagingPort.sendCommandAndAwaitReply(command, TIMEOUT);
        return mapToProductReply(rawReply);
    }

    @Override
    public Reply<Product> getProductById(Long id) {
        Command<Void> command = Command.<Void>builder()
                .type("READ")
                .id(id)
                .build();

        Reply<?> rawReply = productMessagingPort.sendCommandAndAwaitReply(command, TIMEOUT);
        return mapToProductReply(rawReply);
    }

    @Override
    public Reply<List<Product>> getAllProducts() {
        Command<Void> command = Command.<Void>builder()
                .type("READ ALL")
                .build();

        Reply<?> rawReply = productMessagingPort.sendCommandAndAwaitReply(command, TIMEOUT);
        return mapToProductListReply(rawReply);
    }

    @Override
    public Reply<Product> updateProduct(Long id, Product product) {
        Command<Product> command = Command.<Product>builder()
                .type("UPDATE")
                .id(id)
                .body(product)
                .build();

        Reply<?> rawReply = productMessagingPort.sendCommandAndAwaitReply(command, TIMEOUT);
        return mapToProductReply(rawReply);
    }

    @Override
    public Reply<Product> deleteProduct(Long id) {
        Command<Void> command = Command.<Void>builder()
                .type("DELETE")
                .id(id)
                .build();

        Reply<?> rawReply = productMessagingPort.sendCommandAndAwaitReply(command, TIMEOUT);
        return mapToProductReply(rawReply);
    }

    private Reply<Product> mapToProductReply(Reply<?> rawReply) {
        if (rawReply == null) {
            return Reply.<Product>builder().status("ERROR").message("Timeout waiting for reply").build();
        }
        Product parsedBody = null;
        if (rawReply.body() != null) {
            parsedBody = objectMapper.convertValue(rawReply.body(), Product.class);
        }
        return Reply.<Product>builder()
                .status(rawReply.status())
                .message(rawReply.message())
                .body(parsedBody)
                .build();
    }

    private Reply<List<Product>> mapToProductListReply(Reply<?> rawReply) {
        if (rawReply == null) {
            return Reply.<List<Product>>builder().status("ERROR").message("Timeout waiting for reply").build();
        }
        List<Product> parsedBody = null;
        if (rawReply.body() != null) {
            parsedBody = objectMapper.convertValue(rawReply.body(), new TypeReference<List<Product>>() {});
        }
        return Reply.<List<Product>>builder()
                .status(rawReply.status())
                .message(rawReply.message())
                .body(parsedBody)
                .build();
    }
}
