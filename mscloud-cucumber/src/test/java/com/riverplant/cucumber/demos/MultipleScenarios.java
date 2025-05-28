package com.riverplant.cucumber.demos;

import io.cucumber.java.en.*;

public class MultipleScenarios {

    @Given("g")
    public void g() {
       System.out.println("g");
    }

    @When("w")
    public void w() {
        System.out.println("w");
    }

    @Then("t")
    public void t() {
        System.out.println("t");
    }

    @Given("gg")
    public void gg() {
        System.out.println("gg");
    }

    @When("ww")
    public void ww() {
        System.out.println("ww");
    }

    @Then("tt")
    public void tt() {
        System.out.println("tt");
    }


    @Given("ggg")
    public void ggg() {
        System.out.println("ggg");
    }

    @When("www")
    public void www() {
        System.out.println("www");
    }

    @Then("ttt")
    public void ttt() {
        System.out.println("ttt");
    }


}
