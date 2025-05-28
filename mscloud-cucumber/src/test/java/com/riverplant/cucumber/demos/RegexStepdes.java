package com.riverplant.cucumber.demos;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class RegexStepdes
{

    // -? 适配整数或者负数? 代表可有可无
    @Given("^the number (\\d+) and number (-?\\d+)$")
    public void g( int x, int y) {

        System.out.println(x);
        System.out.println(y);
    }

    @When("take the add action")
    public void takeTheAddAction() {

    }
}
