package com.pragma.ordersystem.domain.model;

import java.util.Objects;

/**
 * Representa un producto disponible en el inventario.
 * Contiene información sobre el precio, disponibilidad y cantidad.
 */
public class Product {
    private final String id;
    private final String name;
    private final double price;
    private int quantity;
    private boolean available;

    /**
     * Constructor para crear un nuevo producto.
     * @param id Identificador del producto.
     * @param name Nombre del producto.
     * @param price Precio del producto.
     * @param quantity Cantidad disponible.
     */
    public Product(String id, String name, double price, int quantity) {
        this.id = Objects.requireNonNull(id, "Product ID cannot be null");
        this.name = Objects.requireNonNull(name, "Product name cannot be null");
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
        this.available = quantity > 0;
    }

    /**
     * Actualiza la cantidad disponible del producto.
     * @param newQuantity Nueva cantidad.
     * @throws IllegalArgumentException Si la nueva cantidad es negativa.
     */
    public void setQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = newQuantity;
        this.available = newQuantity > 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }
}