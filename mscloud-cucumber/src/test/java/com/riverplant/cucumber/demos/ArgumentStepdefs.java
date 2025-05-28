package com.riverplant.cucumber.demos;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentStepdefs {

    private String name;
    private int age;
    private String result;


    @Then("^the format string is \"(.*?)\"$")
    public void theFormatStringIsRiver(String arg0) {
        assertEquals(arg0, result);
    }

    @When("format the input")
    public void formatTheInput() {
        this.result = name + "-" + age;
    }

    @Given("the name is {string} and age is {int}")
    public void theNameIsAndAgeIs(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
