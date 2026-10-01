package com.pragma.ordersystem.domain.exception;

/**
 * Excepción especializada que se lanza cuando el proceso de pago falla.
 * Representa errores en la comunicación con el motor de pago o datos de pago inválidos.
 */
public class PaymentFailedException extends RuntimeException {
    
    private final String orderId;
    private final String paymentMethod;
    private final int attemptNumber;
    
    /**
     * Constructor básico que inicializa la excepción con un mensaje descriptivo.
     * @param message Descripción del error de pago
     */
    public PaymentFailedException(String message) {
        super(message);
        this.orderId = null;
        this.paymentMethod = null;
        this.attemptNumber = 0;
    }
    
    /**
     * Constructor completo que incluye contexto del pago que falló.
     * @param message Descripción del error de pago
     * @param orderId Identificador del pedido asociado al pago
     * @param paymentMethod Método de pago utilizado
     * @param attemptNumber Número de intento de pago
     */
    public PaymentFailedException(String message, String orderId, String paymentMethod, int attemptNumber) {
        super(message);
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.attemptNumber = attemptNumber;
    }
    
    /**
     * Constructor que incluye la causa raíz del fallo.
     * @param message Descripción del error de pago
     * @param cause Excepción que originó el fallo
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
        this.orderId = null;
        this.paymentMethod = null;
        this.attemptNumber = 0;
    }
    
    /**
     * Constructor completo con causa raíz y contexto.
     * @param message Descripción del error de pago
     * @param orderId Identificador del pedido asociado al pago
     * @param paymentMethod Método de pago utilizado
     * @param attemptNumber Número de intento de pago
     * @param cause Excepción que originó el fallo
     */
    public PaymentFailedException(String message, String orderId, String paymentMethod, 
                                   int attemptNumber, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.attemptNumber = attemptNumber;
    }
    
    /**
     * Obtiene el identificador del pedido asociado al pago fallido.
     * @return ID del pedido o null si no está disponible
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * Obtiene el método de pago que se intentó utilizar.
     * @return Método de pago o null si no está disponible
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    /**
     * Obtiene el número de intento de pago.
     * @return Número de intento (0 si no aplica)
     */
    public int getAttemptNumber() {
        return attemptNumber;
    }
    
    /**
     * Proporciona información detallada del fallo para logging.
     * @return String con formato legible para diagnóstico
     */
    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentFailedException: ").append(getMessage());
        if (orderId != null) {
            sb.append(" | OrderID: ").append(orderId);
        }
        if (paymentMethod != null) {
            sb.append(" | Method: ").append(paymentMethod);
        }
        if (attemptNumber > 0) {
            sb.append(" | Attempt: ").append(attemptNumber);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return getDetailedMessage();
    }
}