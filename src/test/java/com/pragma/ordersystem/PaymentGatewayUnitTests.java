package com.pragma.ordersystem;


import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;
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
class PaymentGatewayUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.payment.PaymentGateway paymentGateway;

    @Test
    void shouldProcessPaymentSuccessfully() {
        // Given
        String orderId = "ORD-001";
        double amount = 150.00;
        when(paymentGateway.processPayment(orderId, amount)).thenReturn(true);

        // When
        boolean result = paymentGateway.processPayment(orderId, amount);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenPaymentDeclined() {
        // Given
        String orderId = "ORD-002";
        double amount = 200.00;
        when(paymentGateway.processPayment(orderId, amount)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> {
            if (!paymentGateway.processPayment(orderId, amount)) {
                throw new PaymentFailedException("Payment declined for order: " + orderId);
            }
        }).isInstanceOf(PaymentFailedException.class)
          .hasMessageContaining("Payment declined");
    }

    @Test
    void shouldHandleNetworkFailure() {
        // Given
        String orderId = "ORD-003";
        double amount = 100.00;
        when(paymentGateway.processPayment(orderId, amount))
            .thenThrow(new RuntimeException("Network unavailable"));

        // When & Then
        assertThatThrownBy(() -> paymentGateway.processPayment(orderId, amount))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Network unavailable");
    }

    @Test
    void shouldRetryPaymentOnFailure() {
        // Given
        String orderId = "ORD-004";
        double amount = 250.00;
        when(paymentGateway.processPayment(orderId, amount))
            .thenReturn(false)
            .thenReturn(false)
            .thenReturn(true);

        // When
        boolean firstAttempt = paymentGateway.processPayment(orderId, amount);
        boolean secondAttempt = paymentGateway.processPayment(orderId, amount);
        boolean thirdAttempt = paymentGateway.processPayment(orderId, amount);

        // Then
        assertThat(firstAttempt).isFalse();
        assertThat(secondAttempt).isFalse();
        assertThat(thirdAttempt).isTrue();
    }

    @Test
    void shouldHandleInvalidAmount() {
        // Given
        String orderId = "ORD-005";
        double invalidAmount = -50.00;

        // When & Then
        assertThatThrownBy(() -> paymentGateway.processPayment(orderId, invalidAmount))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldProcessPaymentWithValidCard() {
        // Given
        String orderId = "ORD-006";
        double amount = 500.00;
        String cardToken = "tok_valid_card";
        when(paymentGateway.processPaymentWithToken(orderId, amount, cardToken)).thenReturn(true);

        // When
        boolean result = paymentGateway.processPaymentWithToken(orderId, amount, cardToken);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldHandleExpiredCard() {
        // Given
        String orderId = "ORD-007";
        double amount = 75.00;
        String expiredCardToken = "tok_expired";
        when(paymentGateway.processPaymentWithToken(orderId, amount, expiredCardToken))
            .thenThrow(new PaymentFailedException("Card expired"));

        // When & Then
        assertThatThrownBy(() -> 
            paymentGateway.processPaymentWithToken(orderId, amount, expiredCardToken))
            .isInstanceOf(PaymentFailedException.class)
            .hasMessageContaining("Card expired");
    }
}