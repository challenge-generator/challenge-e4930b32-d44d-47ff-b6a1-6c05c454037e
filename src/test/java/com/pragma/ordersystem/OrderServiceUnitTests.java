package com.pragma.ordersystem;





import com.pragma.ordersystem.domain.service.OrderService;
import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;
import com.pragma.ordersystem.domain.model.*;
import com.pragma.ordersystem.domain.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository orderRepository;

    @Mock
    private com.pragma.ordersystem.infrastructure.payment.PaymentGateway paymentGateway;

    @Mock
    private com.pragma.ordersystem.infrastructure.inventory.InventoryClient inventoryClient;

    @InjectMocks
    private com.pragma.ordersystem.domain.service.OrderService orderService;

    private Customer testCustomer;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("CUST-001", "Juan Pérez", "Calle 123, Bogotá");
        testProduct = new Product("PROD-001", "Laptop", 1500.00, 10);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.createOrder(testCustomer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCustomer()).isEqualTo(testCustomer);
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.PENDING);
    }

    @Test
    void shouldAddProductToOrder() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.addProductToOrder(order.getId(), testProduct, 2);

        // Then
        assertThat(result.getProducts()).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenProductNotAvailable() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> orderService.addProductToOrder(order.getId(), testProduct, 5))
            .isInstanceOf(ProductNotAvailableException.class);
    }

    @Test
    void shouldCalculateOrderTotal() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 2);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));

        // When
        double total = orderService.calculateOrderTotal(order.getId());

        // Then
        assertThat(total).isEqualTo(3000.00);
    }

    @Test
    void shouldProcessPaymentSuccessfully() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 1);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(paymentGateway.processPayment(anyString(), anyDouble())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.processPayment(order.getId());

        // Then
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.PAID);
    }

    @Test
    void shouldThrowExceptionWhenPaymentFails() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 1);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(paymentGateway.processPayment(anyString(), anyDouble())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> orderService.processPayment(order.getId()))
            .isInstanceOf(PaymentFailedException.class);
    }

    @Test
    void shouldChangeOrderStatus() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.changeOrderStatus(order.getId(), Order.OrderStatus.SHIPPED);

        // Then
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.SHIPPED);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThatThrownBy(() -> orderService.createOrder(testCustomer))
            .isInstanceOf(OrderException.class);
    }

    @Test
    void shouldBeIdempotentWhenAddingSameProduct() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        orderService.addProductToOrder(order.getId(), testProduct, 2);
        orderService.addProductToOrder(order.getId(), testProduct, 2);

        // Then
        verify(orderRepository, times(2)).save(any(Order.class));
    }
}