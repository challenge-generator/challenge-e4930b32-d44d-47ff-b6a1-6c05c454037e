package com.pragma.ordersystem.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.targets.TheTarget;
import org.openqa.selenium.WebElement;

public class OrderTotal {

    public static Question<Double> fromOrderSummary() {
        return new Question<Double>() {
            @Override
            public Double answeredBy(net.serenitybdd.screenplay.Actor actor) {
                return null;
            }
        };
    }

    public static Question<String> displayedInElement(TheTarget target) {
        return new Question<String>() {
            @Override
            public String answeredBy(net.serenitybdd.screenplay.Actor actor) {
                return null;
            }
        };
    }
}