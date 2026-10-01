package com.pragma.ordersystem;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
    features = "src/test/resources/features",
    glue = {"com.pragma.ordersystem.features.steps"},
    tags = "@wip"
)
public class RunCucumberTest {
}