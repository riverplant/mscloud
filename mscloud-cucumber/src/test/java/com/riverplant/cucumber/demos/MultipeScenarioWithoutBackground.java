package com.riverplant.cucumber.demos;

import io.cucumber.java8.En;

public class MultipeScenarioWithoutBackground implements En {
    public MultipeScenarioWithoutBackground() {
        Given("^x and y value$", () -> {
        });
        And("^add operation$", () -> {
        });
        When("^invoke calculate button$", () -> {
        });
        Then("^the result is x\\+y$", () -> {
        });
        And("^sub operation$", () -> {
        });
        Then("^the result is x-y$", () -> {
        });
    }
}
