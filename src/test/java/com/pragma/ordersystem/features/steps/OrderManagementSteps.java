package com.pragma.ordersystem.features.steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.TheActor;
import org.assertj.core.api.Assertions;

public class OrderManagementSteps {

    @Dado("que el cliente {string} ha iniciado un nuevo pedido")
    public void queElClienteHaIniciadoUnNuevoPedido(String customerName) {
    }

    @Cuando("agrega el producto {string} con cantidad {int}")
    public void agregaElProductoConCantidad(String productName, int quantity) {
    }

    @Cuando("el sistema procesa el pago por {double}")
    public void elSistemaProcesaElPagoPor(double amount) {
    }

    @Cuando("el sistema verifica el inventario del producto {string}")
    public void elSistemaVerificaElInventarioDelProducto(String productName) {
    }

    @Entonces("el total del pedido debe ser {double}")
    public void elTotalDelPedidoDebeSer(double expectedTotal) {
    }

    @Entonces("el estado del pedido debe ser {string}")
    public void elEstadoDelPedidoDebeSer(String expectedStatus) {
    }

    @Entonces("el sistema debe mostrar un mensaje de error indicando {string}")
    public void elSistemaDebeMostrarUnMensajeDeErrorIndicando(String errorMessage) {
    }

    @Dado("que existe un pedido con id {string} en estado {string}")
    public void queExisteUnPedidoConIdEnEstado(String orderId, String status) {
    }

    @Cuando("el administrador cambia el estado del pedido a {string}")
    public void elAdministradorCambiaElEstadoDelPedidoA(String newStatus) {
    }

    @Entonces("el pedido debe aparecer en la lista de pedidos con estado {string}")
    public void elPedidoDebeAppearEnLaListaDePedidosConEstado(String expectedStatus) {
    }
}