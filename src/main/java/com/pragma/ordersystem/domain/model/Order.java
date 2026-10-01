package com.pragma.ordersystem.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa un pedido realizado por un cliente.
 * Contiene información sobre los productos incluidos, el estado del pedido y el total.
 */
public class Order {
    private final String id;
    private final LocalDateTime orderDate;
    private final Customer customer;
    private final List<Product> products;
    private OrderStatus status;

    public enum OrderStatus {
        CREATED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    /**
     * Constructor para crear un nuevo pedido.
     * @param customer Cliente que realiza el pedido.
     */
    public Order(Customer customer) {
        this.id = UUID.randomUUID().toString();
        this.orderDate = LocalDateTime.now();
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.products = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    /**
     * Constructor para recrear un pedido existente (usado en repositorios).
     * @param id Identificador del pedido.
     * @param orderDate Fecha del pedido.
     * @param customer Cliente que realizó el pedido.
     * @param products Lista de productos.
     * @param status Estado del pedido.
     */
    public Order(String id, LocalDateTime orderDate, Customer customer, List<Product> products, OrderStatus status) {
        this.id = Objects.requireNonNull(id, "Order ID cannot be null");
        this.orderDate = Objects.requireNonNull(orderDate, "Order date cannot be null");
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.products = new ArrayList<>(Objects.requireNonNull(products, "Products list cannot be null"));
        this.status = Objects.requireNonNull(status, "Order status cannot be null");
    }

    /**
     * Agrega un producto al pedido si está disponible.
     * @param product Producto a agregar.
     * @param quantity Cantidad a agregar.
     * @throws IllegalArgumentException Si el producto es nulo, la cantidad es <= 0 o el producto no está disponible.
     */
    public void addProduct(Product product, int quantity) {
        Objects.requireNonNull(product, "Product cannot be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (!product.isAvailable()) {
            throw new IllegalArgumentException("Product is not available");
        }
        
        // Verificar si el producto ya existe en el pedido para actualizar cantidad
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + quantity);
                return;
            }
        }
        // Si no existe, agregar nuevo producto con la cantidad especificada
        Product productToAdd = new Product(product.getId(), product.getName(), product.getPrice(), quantity);
        products.add(productToAdd);
    }

    /**
     * Calcula el total del pedido sumando el precio de todos los productos.
     * @return Total del pedido.
     */
    public double calculateTotal() {
        return products.stream()                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    /**
     * Cambia el estado del pedido.
     * @param newStatus Nuevo estado.
     * @throws IllegalArgumentException Si el nuevo estado es nulo o la transición no es válida.
     */
    public void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");
        
        // Validar transición de estados
        switch (this.status) {
            case CREATED:
                if (newStatus != OrderStatus.PROCESSING && newStatus != OrderStatus.CANCELLED) {
                    throw new IllegalArgumentException("Invalid status transition from CREATED");
                }
                break;
            case PROCESSING:
                if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
                    throw new IllegalArgumentException("Invalid status transition from PROCESSING");
                }
                break;
            case SHIPPED:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new IllegalArgumentException("Invalid status transition from SHIPPED");
                }
                break;
            case DELIVERED:
            case CANCELLED:
                throw new IllegalArgumentException("Order is already " + this.status + " and cannot be changed");
            default:
                throw new IllegalArgumentException("Unknown current status");
        }
        this.status = newStatus;
    }

    // Getters
    public String getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public OrderStatus getStatus() {
        return status;
    }
}