package com.riverplant.cucumber.demos;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WithScenarioOutlineStepdefs
{
    private boolean login;

    @Given("open the login page testing scenario outline")
    public void openLoginPage() {

        System.out.println("open the login page");
    }


    @When("use {string} with password {string} testing scenario outline")
    public void useWithPasswordTestingScenarioOutline(String username, String password)
    {
        this.login = ("admin".equals(username) && "123456".equals(password)) ||
                ("nina".equals(username) && "654321".equals(password));

    }

    @Then("the user or password is invalid")
    public void theUserOrPasswordIsInvalid() {
        assertThat(this.login, equalTo(false));
    }
    
    @Then("the user or password is correct")
    public void theUserOrPasswordIsCorrect() {
        assertThat(this.login, equalTo(true));
    }

}
