package com.pragma.ordersystem.domain.service;


import com.pragma.ordersystem.domain.model.Customer;
import com.pragma.ordersystem.domain.exception.OrderException;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;
import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Order.OrderStatus;
import com.pragma.ordersystem.domain.model.Product;
import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final InMemoryOrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final PaymentGateway paymentGateway;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MILLIS = 1000;

    public OrderService(InMemoryOrderRepository orderRepository, 
                        InventoryClient inventoryClient, 
                        PaymentGateway paymentGateway) {
        if (orderRepository == null) {
            throw new IllegalArgumentException("OrderRepository cannot be null");
        }
        if (inventoryClient == null) {
            throw new IllegalArgumentException("InventoryClient cannot be null");
        }
        if (paymentGateway == null) {
            throw new IllegalArgumentException("PaymentGateway cannot be null");
        }
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentGateway = paymentGateway;
    }

    public Order createOrder(String customerId, String customerName, String customerAddress) {
        if (customerId == null || customerId.isBlank()) {
            throw new OrderException("Customer ID cannot be null or empty", null, "INVALID_CUSTOMER_ID");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new OrderException("Customer name cannot be null or empty", null, "INVALID_CUSTOMER_NAME");
        }
        if (customerAddress == null || customerAddress.isBlank()) {
            throw new OrderException("Customer address cannot be null or empty", null, "INVALID_ADDRESS");
        }

        String orderId = UUID.randomUUID().toString();
        LocalDateTime orderDate = LocalDateTime.now();
        var customer = new com.pragma.ordersystem.domain.model.Customer(customerId, customerName, customerAddress);
        var order = new Order(orderId, orderDate, customer, List.of(), OrderStatus.PENDING);
        
        orderRepository.save(order);
        return order;
    }

    public Order addProductToOrder(String orderId, String productId, String productName, 
                                    double price, int quantity) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        if (productId == null || productId.isBlank()) {
            throw new OrderException("Product ID cannot be null or empty", null, "INVALID_PRODUCT_ID");
        }
        if (quantity <= 0) {
            throw new OrderException("Quantity must be greater than zero", orderId, "INVALID_QUANTITY");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderException(
                "Cannot add products to an order that is not in PENDING status. Current status: " + order.getStatus(),
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        boolean productExistsInOrder = order.getProducts().stream()
            .anyMatch(p -> p.getId().equals(productId));

        if (productExistsInOrder) {
            Product existingProduct = order.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow();
            
            int newQuantity = existingProduct.getQuantity() + quantity;
            
            if (!inventoryClient.checkAvailability(productId, newQuantity)) {
                throw new ProductNotAvailableException(
                    productId, 
                    newQuantity, 
                    inventoryClient.getAvailableQuantity(productId)
                );
            }
            
            existingProduct.setQuantity(newQuantity);
        } else {
            if (!inventoryClient.checkAvailability(productId, quantity)) {
                throw new ProductNotAvailableException(
                    productId, 
                    quantity, 
                    inventoryClient.getAvailableQuantity(productId)
                );
            }
            
            Product newProduct = new Product(productId, productName, price, quantity);
            order.addProduct(newProduct, quantity);
        }

        orderRepository.update(order);
        return order;
    }

    public double calculateOrderTotal(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        return order.calculateTotal();
    }

    public Order changeOrderStatus(String orderId, OrderStatus newStatus) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        if (newStatus == null) {
            throw new OrderException("New status cannot be null", orderId, "INVALID_STATUS");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        OrderStatus currentStatus = order.getStatus();
        
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new OrderException(
                String.format("Invalid status transition from %s to %s", currentStatus, newStatus),
                orderId,
                "INVALID_STATUS_TRANSITION"
            );
        }

        order.changeStatus(newStatus);
        orderRepository.update(order);
        return order;
    }

    public Order processPayment(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderException(
                "Cannot process payment for an order that is not in PENDING status",
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        double totalAmount = order.calculateTotal();
        if (totalAmount <= 0) {
            throw new OrderException(
                "Cannot process payment for an order with zero or negative total",
                orderId,
                "INVALID_ORDER_AMOUNT"
            );
        }

        boolean paymentProcessed = executePaymentWithRetry(orderId, totalAmount);
        
        if (!paymentProcessed) {
            order.changeStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.update(order);
            throw new PaymentFailedException(
                "Payment failed after maximum retry attempts",
                orderId,
                "PAYMENT_RETRY_EXHAUSTED"
            );
        }

        order.changeStatus(OrderStatus.PAID);
        orderRepository.update(order);
        
        return order;
    }

    public Order confirmOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PAID) {
            throw new OrderException(
                "Cannot confirm an order that has not been paid. Current status: " + order.getStatus(),
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        if (order.getProducts().isEmpty()) {
            throw new OrderException(
                "Cannot confirm an order with no products",
                orderId,
                "EMPTY_ORDER"
            );
        }

        boolean inventoryReserved = reserveInventory(order);
        if (!inventoryReserved) {
            throw new OrderException(
                "Failed to reserve inventory for the order",
                orderId,
                "INVENTORY_RESERVATION_FAILED"
            );
        }

        order.changeStatus(OrderStatus.CONFIRMED);
        orderRepository.update(order);
        
        return order;
    }

    public Order cancelOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        OrderStatus currentStatus = order.getStatus();
        if (currentStatus == OrderStatus.CANCELLED) {
            throw new OrderException(
                "Order is already cancelled",
                orderId,
                "ORDER_ALREADY_CANCELLED"
            );
        }

        if (currentStatus == OrderStatus.DELIVERED) {
            throw new OrderException(
                "Cannot cancel a delivered order",
                orderId,
                "INVALID_STATUS_TRANSITION"
            );
        }

        if (currentStatus == OrderStatus.PAID || currentStatus == OrderStatus.CONFIRMED) {
            refundPayment(orderId);
        }

        order.changeStatus(OrderStatus.CANCELLED);
        orderRepository.update(order);
        
        return order;
    }

    private boolean executePaymentWithRetry(String orderId, double amount) {
        int attempts = 0;
        Exception lastException = null;
        
        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                attempts++;
                boolean result = paymentGateway.processPayment(orderId, amount);
                if (result) {
                    return true;
                }
            } catch (Exception e) {
                lastException = e;
            }
            
            if (attempts < MAX_RETRY_ATTEMPTS) {
                try {
                    Thread.sleep(RETRY_DELAY_MILLIS * attempts);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        return false;
    }

    private boolean reserveInventory(Order order) {
        for (Product product : order.getProducts()) {
            boolean reserved = inventoryClient.reserveStock(product.getId(), product.getQuantity());
            if (!reserved) {
                return false;
            }
        }
        return true;
    }

    private void refundPayment(String orderId) {
        try {
            paymentGateway.refundPayment(orderId);
        } catch (Exception e) {
        }
    }

    private boolean isValidStatusTransition(OrderStatus from, OrderStatus to) {
        return switch (from) {
            case PENDING -> to == OrderStatus.PAID || to == OrderStatus.CANCELLED || to == OrderStatus.PAYMENT_FAILED;
            case PAID -> to == OrderStatus.CONFIRMED || to == OrderStatus.CANCELLED || to == OrderStatus.PAYMENT_FAILED;
            case CONFIRMED -> to == OrderStatus.SHIPPED || to == OrderStatus.CANCELLED;
            case SHIPPED -> to == OrderStatus.DELIVERED;
            case CANCELLED, PAYMENT_FAILED, DELIVERED -> false;
        };
    }

    public Order getOrderById(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            throw new OrderException("Status cannot be null", null, "INVALID_STATUS");
        }
        return orderRepository.findByStatus(status);
    }
}