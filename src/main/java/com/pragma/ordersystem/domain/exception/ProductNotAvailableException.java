package com.pragma.ordersystem.domain.exception;


import com.pragma.ordersystem.domain.model.Product;
public class ProductNotAvailableException extends OrderException {
    private final String productId;
    private final int requestedQuantity;
    private final int availableQuantity;

    public ProductNotAvailableException(String productId, int requestedQuantity, int availableQuantity) {
        super(buildMessage(productId, requestedQuantity, availableQuantity), null, "PRODUCT_NOT_AVAILABLE");
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public ProductNotAvailableException(String productId, int requestedQuantity, int availableQuantity, Throwable cause) {
        super(buildMessage(productId, requestedQuantity, availableQuantity), null, "PRODUCT_NOT_AVAILABLE", cause);
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    private static String buildMessage(String productId, int requestedQuantity, int availableQuantity) {
        return String.format(
            "Product with ID '%s' is not available in the requested quantity. Requested: %d, Available: %d",
            productId, requestedQuantity, availableQuantity
        );
    }

    public String getProductId() {
        return productId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public String toString() {
        return String.format(
            "ProductNotAvailableException{productId='%s', requested=%d, available=%d, message='%s'}",
            productId, requestedQuantity, availableQuantity, getMessage()
        );
    }
}