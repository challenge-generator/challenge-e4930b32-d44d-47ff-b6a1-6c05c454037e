package com.pragma.ordersystem.infrastructure.inventory;

import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryClient {
    private static final Logger logger = LoggerFactory.getLogger(InventoryClient.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 100;
    
    private final Map<String, Integer> inventoryCache;
    private final Random random;
    private boolean simulateFailure;
    private int currentRetryCount;
    
    public InventoryClient() {
        this.inventoryCache = new ConcurrentHashMap<>();
        this.random = new Random();
        this.simulateFailure = false;
        this.currentRetryCount = 0;
    }
    
    public boolean checkProductAvailability(Product product) {
        logger.debug("Verificando disponibilidad del producto: {}", product.getId());
        
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Producto inválido para verificar disponibilidad");
        }
        
        if (simulateFailure) {
            return simulateInventoryCheckWithFailure();
        }
        
        Integer availableQuantity = inventoryCache.get(product.getId());
        boolean isAvailable = availableQuantity != null && availableQuantity > 0;
        
        logger.info("Producto {} disponible: {}", product.getId(), isAvailable);
        return isAvailable;
    }
    
    public void reserveProduct(String productId, int quantity) {
        logger.info("Reservando {} unidades del producto: {}", quantity, productId);
        
        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException("Parámetros de reserva inválidos");
        }
        
        if (simulateFailure) {
            simulateInventoryFailure();
            return;
        }
        
        Integer currentStock = inventoryCache.get(productId);
        if (currentStock == null || currentStock < quantity) {
            logger.error("Stock insuficiente para producto: {}. Stock actual: {}", productId, currentStock);
            throw new ProductNotAvailableException(
                "Producto no disponible en cantidad solicitada. Producto: " + productId + 
                ", Solicitado: " + quantity + ", Disponible: " + (currentStock != null ? currentStock : 0)
            );
        }
        
        inventoryCache.put(productId, currentStock - quantity);
        logger.info("Reserva exitosa. Nuevo stock para {}: {}", productId, currentStock - quantity);
    }
    
    public void releaseProduct(String productId, int quantity) {
        logger.info("Liberando {} unidades del producto: {}", quantity, productId);
        
        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException("Parámetros de liberación inválidos");
        }
        
        if (simulateFailure) {
            simulateInventoryFailure();
            return;
        }
        
        Integer currentStock = inventoryCache.get(productId);
        int newStock = (currentStock != null ? currentStock : 0) + quantity;
        inventoryCache.put(productId, newStock);
        logger.info("Liberación exitosa. Nuevo stock para {}: {}", productId, newStock);
    }
    
    public void initializeStock(String productId, int initialQuantity) {
        logger.info("Inicializando stock para producto {}: {}", productId, initialQuantity);
        
        if (productId == null) {
            throw new IllegalArgumentException("ID de producto no puede ser null");
        }
        
        if (initialQuantity < 0) {
            throw new IllegalArgumentException("La cantidad inicial no puede ser negativa");
        }
        
        inventoryCache.put(productId, initialQuantity);
    }
    
    public int getCurrentStock(String productId) {
        if (productId == null) {
            return 0;
        }
        return inventoryCache.getOrDefault(productId, 0);
    }
    
    public void updateStock(String productId, int newQuantity) {
        logger.info("Actualizando stock para producto {}: {}", productId, newQuantity);
        
        if (productId == null) {
            throw new IllegalArgumentException("ID de producto no puede ser null");
        }
        
        if (newQuantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        
        inventoryCache.put(productId, newQuantity);
    }
    
    public boolean validateBulkAvailability(List<Product> products) {
        logger.debug("Validando disponibilidad masiva de {} productos", products.size());
        
        if (products == null || products.isEmpty()) {
            return false;
        }
        
        for (Product product : products) {
            if (!checkProductAvailability(product)) {
                logger.warn("Producto no disponible en validación masiva: {}", product.getId());
                return false;
            }
        }
        
        logger.info("Todos los productos disponibles para validación masiva");
        return true;
    }
    
    public void reserveBulkProducts(Map<String, Integer> productQuantities) {
        logger.info("Reservando {} productos en lote", productQuantities.size());
        
        if (productQuantities == null || productQuantities.isEmpty()) {
            throw new IllegalArgumentException("Mapa de productos vacío");
        }
        
        Map<String, Integer> reservedProducts = new HashMap<>();
        
        try {
            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                String productId = entry.getKey();
                Integer quantity = entry.getValue();
                
                if (quantity == null || quantity <= 0) {
                    throw new IllegalArgumentException("Cantidad inválida para producto: " + productId);
                }
                
                reserveProduct(productId, quantity);
                reservedProducts.put(productId, quantity);
            }
            logger.info("Reserva en lote completada exitosamente");
        } catch (Exception e) {
            logger.error("Error en reserva en lote, liberando productos reservados: {}", e.getMessage());
            rollbackBulkReservations(reservedProducts);
            throw e;
        }
    }
    
    private void rollbackBulkReservations(Map<String, Integer> reservedProducts) {
        for (Map.Entry<String, Integer> entry : reservedProducts.entrySet()) {
            try {
                releaseProduct(entry.getKey(), entry.getValue());
            } catch (Exception ex) {
                logger.error("Error en rollback para producto {}: {}", entry.getKey(), ex.getMessage());
            }
        }
    }
    
    private boolean simulateInventoryCheckWithFailure() {
        currentRetryCount++;
        logger.warn("Simulando fallo de conexión con inventario. Intento: {}/{}", currentRetryCount, MAX_RETRY_ATTEMPTS);
        
        if (currentRetryCount >= MAX_RETRY_ATTEMPTS) {
            currentRetryCount = 0;
            return random.nextBoolean();
        }
        
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return simulateInventoryCheckWithFailure();
    }
    
    private void simulateInventoryFailure() {
        currentRetryCount++;
        logger.warn("Simulando fallo de inventario. Intento: {}/{}", currentRetryCount, MAX_RETRY_ATTEMPTS);
        
        if (currentRetryCount >= MAX_RETRY_ATTEMPTS) {
            currentRetryCount = 0;
            throw new RuntimeException("Error de conexión con el sistema de inventario después de " + MAX_RETRY_ATTEMPTS + " intentos");
        }
        
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        simulateInventoryFailure();
    }
    
    public void enableFailureSimulation() {
        this.simulateFailure = true;
        this.currentRetryCount = 0;
        logger.info("Simulación de fallos habilitada");
    }
    
    public void disableFailureSimulation() {
        this.simulateFailure = false;
        this.currentRetryCount = 0;
        logger.info("Simulación de fallos deshabilitada");
    }
    
    public void resetInventory() {
        inventoryCache.clear();
        currentRetryCount = 0;
        logger.info("Inventario reseteado");
    }
    
    public Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventoryCache);
    }
}