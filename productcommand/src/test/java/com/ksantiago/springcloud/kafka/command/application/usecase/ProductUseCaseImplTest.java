package com.ksantiago.springcloud.kafka.command.application.usecase;

import com.ksantiago.springcloud.kafka.command.application.port.out.ProductPersistencePort;
import com.ksantiago.springcloud.kafka.command.domain.exception.ProductNotFoundException;
import com.ksantiago.springcloud.kafka.command.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseImplTest {

    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCaseImpl productUseCase;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(1500.0)
                .build();
    }

    @Test
    void create_ShouldReturnSavedProduct() {
        // Arrange
        when(productPersistencePort.save(any(Product.class))).thenReturn(sampleProduct);

        // Act
        Product result = productUseCase.create(sampleProduct);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop");
        verify(productPersistencePort).save(sampleProduct);
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productPersistencePort.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // Act
        Product result = productUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(productPersistencePort).findById(1L);
    }

    @Test
    void findById_WhenProductDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(productPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act
        Product result = productUseCase.findById(1L);

        // Assert
        assertThat(result).isNull();
        verify(productPersistencePort).findById(1L);
    }

    @Test
    void findAll_ShouldReturnProductList() {
        // Arrange
        List<Product> products = List.of(sampleProduct);
        when(productPersistencePort.findAll()).thenReturn(products);

        // Act
        List<Product> result = productUseCase.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
        verify(productPersistencePort).findAll();
    }

    @Test
    void update_WhenProductExists_ShouldReturnUpdatedProduct() {
        // Arrange
        Product updateRequest = Product.builder().name("Updated Laptop").price(1600.0).build();
        when(productPersistencePort.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productPersistencePort.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productUseCase.update(1L, updateRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Laptop");
        assertThat(result.getPrice()).isEqualTo(1600.0);
        verify(productPersistencePort).findById(1L);
        verify(productPersistencePort).save(any(Product.class));
    }

    @Test
    void update_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        Product updateRequest = Product.builder().name("Updated Laptop").price(1600.0).build();
        when(productPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> productUseCase.update(1L, updateRequest))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product with id 1 not found");
        
        verify(productPersistencePort).findById(1L);
        verify(productPersistencePort, never()).save(any());
    }

    @Test
    void delete_WhenProductExists_ShouldReturnTrue() {
        // Arrange
        when(productPersistencePort.findById(1L)).thenReturn(Optional.of(sampleProduct));
        doNothing().when(productPersistencePort).delete(sampleProduct);

        // Act
        boolean result = productUseCase.delete(1L);

        // Assert
        assertThat(result).isTrue();
        verify(productPersistencePort).findById(1L);
        verify(productPersistencePort).delete(sampleProduct);
    }

    @Test
    void delete_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> productUseCase.delete(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product with id 1 not found");
        
        verify(productPersistencePort).findById(1L);
        verify(productPersistencePort, never()).delete(any());
    }
}
