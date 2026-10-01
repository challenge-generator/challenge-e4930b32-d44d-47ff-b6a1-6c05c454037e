package com.pragma.ordersystem.infrastructure.payment;


import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cliente que simula la interacción con el motor de pago externo.
 * Implementa tolerancia a fallos mediante reintentos automáticos y manejo de excepciones.
 */
public class PaymentGateway {
    
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 1000;
    private static final double SUCCESS_RATE = 0.85;
    
    private final Random random;
    private final AtomicInteger paymentCounter;
    private final Map<String, PaymentRecord> paymentHistory;
    private final boolean simulateFailures;
    
    /**
     * Constructor por defecto que inicializa el gateway con simulación de fallos activada.
     */
    public PaymentGateway() {
        this(true);
    }
    
    /**
     * Constructor que permite configurar la simulación de fallos.
     * @param simulateFailures true para simular fallos aleatorios, false para entorno estable
     */
    public PaymentGateway(boolean simulateFailures) {
        this.random = new Random();
        this.paymentCounter = new AtomicInteger(0);
        this.paymentHistory = new HashMap<>();
        this.simulateFailures = simulateFailures;
    }
    
    /**
     * Procesa el pago de un pedido.
     * Implementa reintentos automáticos en caso de fallo temporal.
     * @param orderId Identificador del pedido a pagar
     * @param amount Monto total a pagar
     * @param paymentMethod Método de pago (tarjeta, PayPal, etc.)
     * @return TransactionResult con el resultado del procesamiento
     * @throws PaymentFailedException si el pago falla después de todos los reintentos
     */
    public TransactionResult processPayment(String orderId, double amount, String paymentMethod) {
        validatePaymentRequest(orderId, amount, paymentMethod);
        
        int attempt = 0;
        PaymentFailedException lastException = null;
        
        while (attempt < MAX_RETRY_ATTEMPTS) {
            attempt++;
            try {
                return executePaymentAttempt(orderId, amount, paymentMethod, attempt);
            } catch (PaymentFailedException e) {
                lastException = e;
                if (!isRetryable(e) || attempt >= MAX_RETRY_ATTEMPTS) {
                    break;
                }
                waitBeforeRetry(attempt);
            }
        }
        
        PaymentFailedException finalException = new PaymentFailedException(
            "Payment failed after " + MAX_RETRY_ATTEMPTS + " attempts",
            orderId,
            paymentMethod,
            attempt,
            lastException
        );
        throw finalException;
    }
    
    /**
     * Valida los parámetros de la solicitud de pago.
     * @param orderId Identificador del pedido
     * @param amount Monto a pagar
     * @param paymentMethod Método de pago
     */
    private void validatePaymentRequest(String orderId, double amount, String paymentMethod) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
    }
    
    /**
     * Ejecuta un intento de pago individual.
     * @param orderId Identificador del pedido
     * @param amount Monto a pagar
     * @param paymentMethod Método de pago
     * @param attemptNumber Número de intento
     * @return TransactionResult con el resultado
     */
    private TransactionResult executePaymentAttempt(String orderId, double amount, 
                                                      String paymentMethod, int attemptNumber) {
        int transactionId = paymentCounter.incrementAndGet();
        
        if (simulateFailures && random.nextDouble() > SUCCESS_RATE) {
            String failureReason = generateRandomFailureReason();
            throw new PaymentFailedException(
                failureReason,
                orderId,
                paymentMethod,
                attemptNumber
            );
        }
        
        String authorizationCode = generateAuthorizationCode(transactionId);
        PaymentRecord record = new PaymentRecord(
            transactionId,
            orderId,
            amount,
            paymentMethod,
            "APPROVED",
            authorizationCode,
            LocalDateTime.now()
        );
        
        paymentHistory.put(orderId, record);
        
        return new TransactionResult(
            true,
            transactionId,
            authorizationCode,
            "Payment processed successfully",
            null
        );
    }
    
    /**
     * Determina si un error es susceptible de reintento.
     * @param exception Excepción ocurrida
     * @return true si se debe reintentar, false si es un error permanente
     */
    private boolean isRetryable(PaymentFailedException exception) {
        String message = exception.getMessage().toLowerCase();
        return message.contains("timeout") || 
               message.contains("connection") || 
               message.contains("temporary") ||
               message.contains("retry");
    }
    
    /**
     * Espera antes de reintentar el pago.
     * @param attemptNumber Número de intento actual
     */
    private void waitBeforeRetry(int attemptNumber) {
        try {
            Thread.sleep(RETRY_DELAY_MS * attemptNumber);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Genera una razón de fallo aleatoria para simulación.
     * @return String con la razón del fallo
     */
    private String generateRandomFailureReason() {
        String[] failureReasons = {
            "Connection timeout with payment provider",
            "Temporary service unavailable",
            "Payment gateway internal error",
            "Network connectivity issue",
            "Bank gateway timeout"
        };
        return failureReasons[random.nextInt(failureReasons.length)];
    }
    
    /**
     * Genera un código de autorización único para transacciones exitosas.
     * @param transactionId ID de la transacción
     * @return Código de autorización formateado
     */
    private String generateAuthorizationCode(int transactionId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "AUTH-" + timestamp + "-" + transactionId;
    }
    
    /**
     * Consulta el estado de un pago realizado previamente.
     * @param orderId Identificador del pedido
     * @return TransactionResult con el estado actual o null si no existe
     */
    public TransactionResult getPaymentStatus(String orderId) {
        PaymentRecord record = paymentHistory.get(orderId);
        if (record == null) {
            return null;
        }
        return new TransactionResult(
            "APPROVED".equals(record.status),
            record.transactionId,
            record.authorizationCode,
            "Status: " + record.status,
            null
        );
    }
    
    /**
     * Reinicia el contador de transacciones y el historial.
     * Útil para pruebas que requieren un estado limpio.
     */
    public void reset() {
        paymentCounter.set(0);
        paymentHistory.clear();
    }
    
    /**
     * Registro interno de un pago procesado.
     */
    private static class PaymentRecord {
        final int transactionId;
        final String orderId;
        final double amount;
        final String paymentMethod;
        final String status;
        final String authorizationCode;
        final LocalDateTime timestamp;
        
        PaymentRecord(int transactionId, String orderId, double amount, String paymentMethod,
                      String status, String authorizationCode, LocalDateTime timestamp) {
            this.transactionId = transactionId;
            this.orderId = orderId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.status = status;
            this.authorizationCode = authorizationCode;
            this.timestamp = timestamp;
        }
    }
    
    /**
     * Resultado de una operación de pago.
     */
    public static class TransactionResult {
        private final boolean successful;
        private final int transactionId;
        private final String authorizationCode;
        private final String message;
        private final String errorCode;
        
        public TransactionResult(boolean successful, int transactionId, String authorizationCode,
                                  String message, String errorCode) {
            this.successful = successful;
            this.transactionId = transactionId;
            this.authorizationCode = authorizationCode;
            this.message = message;
            this.errorCode = errorCode;
        }
        
        public boolean isSuccessful() {
            return successful;
        }
        
        public int getTransactionId() {
            return transactionId;
        }
        
        public String getAuthorizationCode() {
            return authorizationCode;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getErrorCode() {
            return errorCode;
        }
    }
}