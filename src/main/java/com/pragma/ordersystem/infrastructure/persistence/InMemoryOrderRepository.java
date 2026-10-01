package com.pragma.ordersystem.infrastructure.persistence;

import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Order.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de pedidos.
 * Simula la persistencia utilizando un ConcurrentHashMap para almacenar los pedidos.
 * Esta implementación es útil para pruebas y entornos de desarrollo sin base de datos.
 */
public class InMemoryOrderRepository {
    
    private final Map<String, Order> orders;
    private final AtomicLong idGenerator;
    
    /**
     * Constructor que inicializa el repositorio con un mapa vacío.
     */
    public InMemoryOrderRepository() {
        this.orders = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicLong(System.currentTimeMillis());
    }
    
    /**
     * Guarda un nuevo pedido en el repositorio.
     * Si el pedido ya existe, actualiza sus datos.
     * @param order Pedido a guardar
     * @return El pedido guardado con su ID asignado
     */
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("El pedido no puede ser null");
        }
        
        String orderId = order.getId();
        if (orderId == null || orderId.isEmpty()) {
            orderId = generateOrderId();
        }
        
        Order orderToSave = new Order(
            orderId,
            order.getOrderDate(),
            order.getCustomer(),
            order.getProducts(),
            order.getStatus()
        );
        
        orders.put(orderId, orderToSave);
        return orderToSave;
    }
    
    /**
     * Busca un pedido por su identificador único.
     * @param id Identificador del pedido
     * @return Optional conteniendo el pedido si existe, o vacío si no se encuentra
     */
    public Optional<Order> findById(String id) {
        if (id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(id));
    }
    
    /**
     * Recupera todos los pedidos almacenados en el repositorio.
     * @return Lista inmutable de todos los pedidos
     */
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
    
    /**
     * Busca pedidos por estado específico.
     * @param status Estado de los pedidos a buscar
     * @return Lista de pedidos que coinciden con el estado
     */
    public List<Order> findByStatus(OrderStatus status) {
        if (status == null) {
            return List.of();
        }
        return orders.values().stream()
            .filter(order -> order.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca pedidos de un cliente específico.
     * @param customerId Identificador del cliente
     * @return Lista de pedidos del cliente
     */
    public List<Order> findByCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return List.of();
        }
        return orders.values().stream()
            .filter(order -> order.getCustomer() != null)
            .filter(order -> customerId.equals(order.getCustomer().getId()))
            .collect(Collectors.toList());
    }
    
    /**
     * Actualiza el estado de un pedido existente.
     * @param orderId Identificador del pedido
     * @param newStatus Nuevo estado a asignar
     * @return Optional con el pedido actualizado, o vacío si no existe
     */
    public Optional<Order> updateStatus(String orderId, OrderStatus newStatus) {
        if (orderId == null || newStatus == null) {
            return Optional.empty();
        }
        
        Order existingOrder = orders.get(orderId);
        if (existingOrder == null) {
            return Optional.empty();
        }
        
        Order updatedOrder = new Order(
            existingOrder.getId(),
            existingOrder.getOrderDate(),
            existingOrder.getCustomer(),
            existingOrder.getProducts(),
            newStatus
        );
        
        orders.put(orderId, updatedOrder);
        return Optional.of(updatedOrder);
    }
    
    /**
     * Elimina un pedido del repositorio.
     * @param orderId Identificador del pedido a eliminar
     * @return true si el pedido fue eliminado, false si no existía
     */
    public boolean delete(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return false;
        }
        return orders.remove(orderId) != null;
    }
    
    /**
     * Verifica si existe un pedido con el identificador dado.
     * @param orderId Identificador del pedido
     * @return true si existe, false en caso contrario
     */
    public boolean existsById(String orderId) {
        return orderId != null && !orderId.isEmpty() && orders.containsKey(orderId);
    }
    
    /**
     * Cuenta el número total de pedidos en el repositorio.
     * @return Cantidad de pedidos almacenados
     */
    public long count() {
        return orders.size();
    }
    
    /**
     * Limpia todos los pedidos del repositorio.
     * Útil para pruebas que requieren un estado limpio.
     */
    public void clear() {
        orders.clear();
    }
    
    /**
     * Genera un identificador único para nuevos pedidos.
     * @return String con formato "ORD-" seguido de un número secuencial
     */
    private String generateOrderId() {
        return "ORD-" + idGenerator.getAndIncrement();
    }
}