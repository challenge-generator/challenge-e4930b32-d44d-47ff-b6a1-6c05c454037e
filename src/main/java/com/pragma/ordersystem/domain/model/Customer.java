package com.pragma.ordersystem.domain.model;

import java.util.Objects;

/**
 * Representa un cliente del sistema.
 * Contiene información básica como nombre y dirección.
 */
public class Customer {
    private final String id;
    private final String name;
    private final String address;

    /**
     * Constructor para crear un nuevo cliente.
     * @param id Identificador del cliente.
     * @param name Nombre del cliente.
     * @param address Dirección del cliente.
     */
    public Customer(String id, String name, String address) {
        this.id = Objects.requireNonNull(id, "Customer ID cannot be null");
        this.name = Objects.requireNonNull(name, "Customer name cannot be null");
        this.address = Objects.requireNonNull(address, "Customer address cannot be null");
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}