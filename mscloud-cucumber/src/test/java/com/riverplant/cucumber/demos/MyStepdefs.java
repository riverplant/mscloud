package com.riverplant.cucumber.demos;

import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStepdefs implements En {
   private Calculator calculator;
   private int result;

    public MyStepdefs() {
        Given("^x is (\\d+) and y is (\\d+)$", (Integer arg0, Integer arg1) -> {
            calculator = new Calculator(arg0,arg1);
        });
        When("^invoke add method$", () -> {
            result = calculator.add();
        });
        Then("^the result is (\\d+)$", (Integer arg0) -> {
            assertEquals(arg0, result);
        });
    }
}
