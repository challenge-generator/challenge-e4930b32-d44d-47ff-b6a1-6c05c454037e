package com.pragma.ordersystem.domain.exception;


import com.pragma.ordersystem.domain.model.Order;
public class OrderException extends RuntimeException {
    private final String orderId;
    private final String errorCode;

    public OrderException(String message) {
        super(message);
        this.orderId = null;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId) {
        super(message);
        this.orderId = orderId;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, String errorCode) {
        super(message);
        this.orderId = orderId;
        this.errorCode = errorCode;
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
        this.orderId = null;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, String errorCode, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.errorCode = errorCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OrderException: ");
        sb.append(getMessage());
        if (orderId != null) {
            sb.append(" [Order ID: ").append(orderId).append("]");
        }
        if (errorCode != null) {
            sb.append(" [Code: ").append(errorCode).append("]");
        }
        return sb.toString();
    }
}