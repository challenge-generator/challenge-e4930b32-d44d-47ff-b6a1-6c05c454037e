package com.pragma.ordersystem.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.pragma.ordersystem.features.steps"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "net.serenitybdd.cucumber. SerenityReporter"
    },
    tags = "@wip",
    monochrome = true,
    dryRun = false
)
public class OrderManagementTest {
}