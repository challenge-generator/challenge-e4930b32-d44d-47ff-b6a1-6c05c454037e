package com.pragma.ordersystem;


import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InventoryClientUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.inventory.InventoryClient inventoryClient;

    @Test
    void shouldCheckProductAvailability() {
        // Given
        String productId = "PROD-001";
        int requestedQuantity = 5;
        when(inventoryClient.checkAvailability(productId, requestedQuantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.checkAvailability(productId, requestedQuantity);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenInsufficientStock() {
        // Given
        String productId = "PROD-002";
        int requestedQuantity = 100;
        when(inventoryClient.checkAvailability(productId, requestedQuantity)).thenReturn(false);

        // When
        boolean result = inventoryClient.checkAvailability(productId, requestedQuantity);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        String nonExistentProductId = "PROD-999";
        when(inventoryClient.checkAvailability(nonExistentProductId, 1))
            .thenThrow(new ProductNotAvailableException("Product not found: " + nonExistentProductId));

        // When & Then
        assertThatThrownBy(() -> 
            inventoryClient.checkAvailability(nonExistentProductId, 1))
            .isInstanceOf(ProductNotAvailableException.class)
            .hasMessageContaining("Product not found");
    }

    @Test
    void shouldReduceStockAfterOrderConfirmation() {
        // Given
        String productId = "PROD-003";
        int quantity = 3;
        when(inventoryClient.reduceStock(productId, quantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.reduceStock(productId, quantity);

        // Then
        assertThat(result).isTrue();
        verify(inventoryClient).reduceStock(productId, quantity);
    }

    @Test
    void shouldHandleStockReductionFailure() {
        // Given
        String productId = "PROD-004";
        int quantity = 10;
        when(inventoryClient.reduceStock(productId, quantity)).thenReturn(false);

        // When
        boolean result = inventoryClient.reduceStock(productId, quantity);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckAvailabilityForZeroQuantity() {
        // Given
        String productId = "PROD-005";
        int zeroQuantity = 0;
        when(inventoryClient.checkAvailability(productId, zeroQuantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.checkAvailability(productId, zeroQuantity);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldHandleConcurrentStockRequests() {
        // Given
        String productId = "PROD-006";
        int quantity = 2;
        when(inventoryClient.checkAvailability(productId, quantity))
            .thenReturn(true)
            .thenReturn(true);

        // When
        boolean firstRequest = inventoryClient.checkAvailability(productId, quantity);
        boolean secondRequest = inventoryClient.checkAvailability(productId, quantity);

        // Then
        assertThat(firstRequest).isTrue();
        assertThat(secondRequest).isTrue();
    }

    @Test
    void shouldReturnProductDetails() {
        // Given
        String productId = "PROD-007";
        Product expectedProduct = new Product(productId, "Test Product", 99.99, 50);
        when(inventoryClient.getProductDetails(productId)).thenReturn(expectedProduct);

        // When
        Product result = inventoryClient.getProductDetails(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo("Test Product");
    }
}