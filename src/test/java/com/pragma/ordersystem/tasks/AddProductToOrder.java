package com.pragma.ordersystem.tasks;

import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Product;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.annotations.Step;

import java.time.LocalDateTime;

public class AddProductToOrder implements Task {

    private final String orderId;
    private final String productId;
    private final String productName;
    private final double productPrice;
    private final int quantity;
    private final InMemoryOrderRepository orderRepository;

    public AddProductToOrder(String orderId, String productId, String productName, 
                              double productPrice, int quantity, InMemoryOrderRepository orderRepository) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.orderRepository = orderRepository;
    }

    @Override
    @Step("{0} agrega el producto #productName con cantidad #quantity al pedido #orderId")
    public <T extends Actor> void performAs(T actor) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + orderId));

        Product product = new Product(productId, productName, productPrice, quantity);
        order.addProduct(product, quantity);

        orderRepository.save(order);
    }

    public static AddProductToOrderBuilder withProduct(String productId, String productName, 
                                                        double productPrice, int quantity) {
        return new AddProductToOrderBuilder(productId, productName, productPrice, quantity);
    }

    public static class AddProductToOrderBuilder {
        private final String productId;
        private final String productName;
        private final double productPrice;
        private final int quantity;
        private InMemoryOrderRepository orderRepository;
        private String orderId;

        public AddProductToOrderBuilder(String productId, String productName, 
                                         double productPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }

        public AddProductToOrderBuilder toOrder(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public AddProductToOrderBuilder withRepository(InMemoryOrderRepository orderRepository) {
            this.orderRepository = orderRepository;
            return this;
        }

        public AddProductToOrder build() {
            return new AddProductToOrder(orderId, productId, productName, 
                                         productPrice, quantity, orderRepository);
        }
    }
}