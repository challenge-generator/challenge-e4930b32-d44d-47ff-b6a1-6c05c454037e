package com.pragma.ordersystem.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Actor;

public class ChangeOrderStatus implements Interaction {

    private final String newStatus;

    public ChangeOrderStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public static ChangeOrderStatus to(String status) {
        return new ChangeOrderStatus(status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
    }
}