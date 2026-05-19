package com.ksantiago.springcloud.products.application.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksantiago.springcloud.products.application.port.out.ProductMessagingPort;
import com.ksantiago.springcloud.products.domain.model.Command;
import com.ksantiago.springcloud.products.domain.model.Product;
import com.ksantiago.springcloud.products.domain.model.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseImplTest {

    @Mock
    private ProductMessagingPort productMessagingPort;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductUseCaseImpl productUseCase;

    @Captor
    private ArgumentCaptor<Command<?>> commandCaptor;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(100.0)
                .build();
    }

    @Test
    void createProduct_ShouldReturnReplyWithProduct() {
        // Arrange
        Reply<?> rawReply = Reply.builder().status("SUCCESS").body(sampleProduct).build();
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenAnswer(invocation -> rawReply);
        when(objectMapper.convertValue(any(), eq(Product.class))).thenReturn(sampleProduct);

        // Act
        Reply<Product> result = productUseCase.createProduct(sampleProduct);

        // Assert
        verify(productMessagingPort).sendCommandAndAwaitReply(commandCaptor.capture(), any(Duration.class));
        Command<?> capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.type()).isEqualTo("CREATE");
        assertThat(capturedCommand.body()).isEqualTo(sampleProduct);

        assertThat(result.status()).isEqualTo("SUCCESS");
        assertThat(result.body()).isEqualTo(sampleProduct);
    }

    @Test
    void getProductById_ShouldReturnReplyWithProduct() {
        // Arrange
        Long productId = 1L;
        Reply<?> rawReply = Reply.builder().status("SUCCESS").body(sampleProduct).build();
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenAnswer(invocation -> rawReply);
        when(objectMapper.convertValue(any(), eq(Product.class))).thenReturn(sampleProduct);

        // Act
        Reply<Product> result = productUseCase.getProductById(productId);

        // Assert
        verify(productMessagingPort).sendCommandAndAwaitReply(commandCaptor.capture(), any(Duration.class));
        Command<?> capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.type()).isEqualTo("READ");
        assertThat(capturedCommand.id()).isEqualTo(productId);

        assertThat(result.status()).isEqualTo("SUCCESS");
        assertThat(result.body()).isEqualTo(sampleProduct);
    }

    @Test
    void getAllProducts_ShouldReturnReplyWithProductList() {
        // Arrange
        List<Product> products = List.of(sampleProduct);
        Reply<?> rawReply = Reply.builder().status("SUCCESS").body(products).build();
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenAnswer(invocation -> rawReply);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(products);

        // Act
        Reply<List<Product>> result = productUseCase.getAllProducts();

        // Assert
        verify(productMessagingPort).sendCommandAndAwaitReply(commandCaptor.capture(), any(Duration.class));
        Command<?> capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.type()).isEqualTo("READ ALL");

        assertThat(result.status()).isEqualTo("SUCCESS");
        assertThat(result.body()).isEqualTo(products);
    }

    @Test
    void updateProduct_ShouldReturnReplyWithProduct() {
        // Arrange
        Long productId = 1L;
        Reply<?> rawReply = Reply.builder().status("SUCCESS").body(sampleProduct).build();
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenAnswer(invocation -> rawReply);
        when(objectMapper.convertValue(any(), eq(Product.class))).thenReturn(sampleProduct);

        // Act
        Reply<Product> result = productUseCase.updateProduct(productId, sampleProduct);

        // Assert
        verify(productMessagingPort).sendCommandAndAwaitReply(commandCaptor.capture(), any(Duration.class));
        Command<?> capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.type()).isEqualTo("UPDATE");
        assertThat(capturedCommand.id()).isEqualTo(productId);
        assertThat(capturedCommand.body()).isEqualTo(sampleProduct);

        assertThat(result.status()).isEqualTo("SUCCESS");
        assertThat(result.body()).isEqualTo(sampleProduct);
    }

    @Test
    void deleteProduct_ShouldReturnReplyWithProduct() {
        // Arrange
        Long productId = 1L;
        Reply<?> rawReply = Reply.builder().status("SUCCESS").body(sampleProduct).build();
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenAnswer(invocation -> rawReply);
        when(objectMapper.convertValue(any(), eq(Product.class))).thenReturn(sampleProduct);

        // Act
        Reply<Product> result = productUseCase.deleteProduct(productId);

        // Assert
        verify(productMessagingPort).sendCommandAndAwaitReply(commandCaptor.capture(), any(Duration.class));
        Command<?> capturedCommand = commandCaptor.getValue();
        assertThat(capturedCommand.type()).isEqualTo("DELETE");
        assertThat(capturedCommand.id()).isEqualTo(productId);

        assertThat(result.status()).isEqualTo("SUCCESS");
        assertThat(result.body()).isEqualTo(sampleProduct);
    }

    @Test
    void whenMessagingPortReturnsNull_ShouldReturnErrorReply() {
        // Arrange
        when(productMessagingPort.sendCommandAndAwaitReply(any(), any(Duration.class)))
                .thenReturn(null);

        // Act
        Reply<Product> result = productUseCase.createProduct(sampleProduct);

        // Assert
        assertThat(result.status()).isEqualTo("ERROR");
        assertThat(result.message()).isEqualTo("Timeout waiting for reply");
        assertThat(result.body()).isNull();
    }
}
