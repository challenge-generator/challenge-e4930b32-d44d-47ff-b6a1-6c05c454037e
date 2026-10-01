Feature: Gestión de Pedidos
  Como cliente del sistema de pedidos
  Quiero gestionar productos en mi pedido
  Para completar una compra en línea

  Background:
    Given que existe un cliente con id "CUST-001" llamado "Juan Pérez" con dirección "Calle Principal 123"
    And que el sistema tiene el pedido con id "ORD-001" en estado "PENDIENTE"

  Scenario: Agregar un producto disponible al pedido
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    When el usuario agrega el producto "PROD-001" con cantidad 2 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener el producto "PROD-001" con cantidad 2
    And el total del pedido debe ser 3000.00

  Scenario: Agregar múltiples productos al pedido
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    And que el producto "PROD-002" con nombre "Mouse" y precio 25.50 está disponible
    When el usuario agrega el producto "PROD-001" con cantidad 1 al pedido "ORD-001"
    And el usuario agrega el producto "PROD-002" con cantidad 3 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener 2 productos
    And el total del pedido debe ser 1576.50

  Scenario: Agregar producto no disponible al pedido
    Given que el producto "PROD-003" con nombre "Teclado" y precio 80.00 no está disponible
    When el usuario intenta agregar el producto "PROD-003" con cantidad 1 al pedido "ORD-001"
    Then el sistema debe mostrar el error "Producto no disponible"
    And el pedido "ORD-001" debe estar vacío

  Scenario: Calcular total del pedido vacío
    Given que el pedido "ORD-001" está vacío
    When se solicita el cálculo del total del pedido "ORD-001"
    Then el total debe ser 0.00

  Scenario: Cambiar estado del pedido a CONFIRMADO
    Given que el pedido "ORD-001" está en estado "PENDIENTE"
    When se cambia el estado del pedido "ORD-001" a "CONFIRMADO"
    Then el pedido "ORD-001" debe estar en estado "CONFIRMADO"

  Scenario: Cambiar estado del pedido a CANCELADO
    Given que el pedido "ORD-001" está en estado "CONFIRMADO"
    When se cambia el estado del pedido "ORD-001" a "CANCELADO"
    Then el pedido "ORD-001" debe estar en estado "CANCELADO"

  Scenario: Idempotencia al agregar el mismo producto
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    And que el pedido "ORD-001" ya contiene el producto "PROD-001" con cantidad 2
    When el usuario agrega el producto "PROD-001" con cantidad 2 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener el producto "PROD-001" con cantidad 4
    And el total del pedido debe ser 6000.00

  Scenario: Procesar pago exitosamente
    Given que el pedido "ORD-001" tiene productos con total 3000.00
    And que el pago está disponible
    When se procesa el pago del pedido "ORD-001"
    Then el pago debe ser exitoso
    And el pedido debe cambiar a estado "PAGADO"

  Scenario: Fallo en el procesamiento de pago
    Given que el pedido "ORD-001" tiene productos con total 5000.00
    And que el pago falla por fondos insuficientes
    When se procesa el pago del pedido "ORD-001"
    Then debe mostrar el error "Pago fallido: fondos insuficientes"
    And el pedido debe mantener el estado "PENDIENTE"